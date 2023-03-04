package com.sunlight.invest.service;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.HttpUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.context.StockContext;
import com.sunlight.invest.dao.SelStockMapper;
import com.sunlight.invest.dao.StocksMapper;
import com.sunlight.invest.model.SelStock;
import com.sunlight.invest.model.Stocks;
import com.sunlight.invest.utils.StockUtils;
import com.sunlight.invest.vo.HttpResult;
import com.sunlight.invest.vo.MonitorPriceVo;
import com.sunlight.invest.vo.SelStockVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("stockService")
public class StockService {

    @Resource
    private SelStockMapper selStockMapper;

    @Resource
    private StocksMapper stocksMapper;

    @Resource
    private RedisService redisService;

    public List<SelStockVo> getSelStocks (Integer companyId){
        List<SelStockVo> ret = new ArrayList<>();
        SelStock ss = new SelStock();
        if (companyId != null) {
            ss.setCompanyId(companyId);
        }
        ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<SelStock> sslist = selStockMapper.selectMany(ss);
        if (sslist != null && sslist.size() > 0) {
            for( SelStock s: sslist) {
                ret.add(new SelStockVo(s));
            }
        }
        return ret;
    }

    /**
     * 第三方自选 添加
     * @param codes codes
     */
    public void addThirdPartSelStock (List<String> codes) {
        SelStock old = new SelStock();
        old.setCodes(codes);
        old.setCompanyId(1); // 暂时波派
        List<SelStock> oldList = selStockMapper.selectMany(old);
        if (oldList.size() > 0) {
            StringBuilder toUpdateIds = new StringBuilder();
            oldList.forEach(o -> {
                codes.remove(o.getCode());
                if (o.getDelstatus().equals(DelStatusEnum.UnDelete.getValue())) {
                    toUpdateIds.append(o.getId()).append(",");
                }
            });
            String ids = toUpdateIds.toString();
            if (ids.length() > 0) {
                ids = ids.substring(0, ids.length() - 1);
                String sql = "update sel_stock set is_third = " + Constant.IS_THIRD_STOCK + ", delstatus = " + DelStatusEnum.UnDelete.getValue() +
                        " where id in (" + ids + ")";
                selStockMapper.executeCustomSql(sql);
            }
        }
        if (codes.size() > 0) {
            // 有新增的
            StringBuilder getInfoCodes = new StringBuilder();
            codes.forEach(c -> {
                getInfoCodes.append(StockUtils.getCodePrefix(c)).append(c).append(",");
            });
            log.info("add third stocks new : " + getInfoCodes.toString());
            String stockStr = StockUtils.getStockDetail(getInfoCodes.toString());
            assert stockStr != null;
            List<StockInfoVo> stocks = StockUtils.paresTXStock(stockStr);
            List<SelStock> toAddList = new ArrayList<>();
            stocks.forEach(ss -> {
                SelStock add = new SelStock();
                add.setIsThird(Constant.IS_THIRD_STOCK);
                add.setDelstatus(DelStatusEnum.UnDelete.getValue());
                add.setCode(ss.getCode());
                add.setName(ss.getName());
                add.setCompanyId(1);
                add.setRemarks("批量加入，暂时只支持波派。。。");
                toAddList.add(add);
            });
            selStockMapper.insertBatch(toAddList);
        }
    }

    /**
     * 每天凌晨删除第三方自选股，避免监控太多
     */
    public void batchDeleteThirdPartSelStock(){
        String sql = "update sel_stock set delstatus = "+ DelStatusEnum.Delete.getValue() + " where is_third = " + Constant.IS_THIRD_STOCK;
        selStockMapper.executeCustomSql(sql);
    }

    public StockInfoVo addSelStock (String code, Integer companyId){
        SelStock old = new SelStock();
        old.setCode(code);
        old.setCompanyId(companyId);
        List<SelStock> olds = selStockMapper.selectMany(old);
        String stockStr = StockUtils.getStockDetail(StockUtils.getCodePrefix(code) + code);
        assert stockStr != null;
        List<StockInfoVo> stocks = StockUtils.paresTXStock(stockStr);
        if (olds.size() > 0) {
            old = olds.get(0);
            if (old.getDelstatus().equals(DelStatusEnum.UnDelete.getValue())) {
                return stocks.get(0);
            } else {
                old.setDelstatus(DelStatusEnum.UnDelete.getValue());
                old.setIsThird(Constant.IS_NOT_THIRD_STOCK);
                selStockMapper.updateByPrimaryKey(old);
                return stocks.get(0);
            }
        }

        SelStock ss = new SelStock();
        ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
        ss.setCode(code);
        ss.setIsThird(Constant.IS_NOT_THIRD_STOCK);
        ss.setName(stocks.get(0).getName());
        ss.setCompanyId(companyId);
        selStockMapper.insert(ss);
        return stocks.get(0);
    }

    public List<StockInfoVo> getStockDetail(String code, Integer companyId) {
        List<StockInfoVo> rets = new ArrayList<>();
        String[] codes = code.split(",");
        StringBuilder fullCodes = new StringBuilder();
        log.debug("getStockDetail" + code);
        for(String c: codes) {
            if (StringUtils.isNotBlank(c)) {
                StockInfoVo cached = StockContext.getInstance().getStockInfoByCode(c);
                if (cached == null) {
                    log.debug("getStockDetail" + code + "缓存不存在，添加新记录。。");
                    fullCodes.append(StockUtils.getCodePrefix(c)).append(c).append(",");
                } else {
                    // 读取缓存
                    MonitorPriceVo vo = StockContext.getInstance().getStockMonitorByCode(c, companyId);
                    cached.setMonitorPriceVo(vo);
                    rets.add(cached);
                }
            }
        }
        String result = fullCodes.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() -1);
        }
        log.debug("添加新股票: [" + result + "];");
        if (StringUtils.isNotBlank(result)) {
            String ret = StockUtils.getStockDetail(result);
            if (ret != null) {
                List<StockInfoVo> news = StockUtils.paresTXStock(ret);
                rets.addAll(news);
                StockContext.getInstance().addCacheSelStockList(news);
            }
        }
        return rets;
    }

    public SelStock getSelStocksByCode(String code, Integer companyId) {
        SelStock ss = new SelStock();
        ss.setCode(code);
        ss.setCompanyId(companyId);
        ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<SelStock> list = selStockMapper.selectMany(ss);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void deleteSelStock(Integer id, String code) {
        SelStock ss = new SelStock();
        ss.setId(id);
        ss.setDelstatus(DelStatusEnum.Delete.getValue());
        selStockMapper.updateByPrimaryKeySelective(ss);
        StockContext.getInstance().deleteCacheSelStock(code);
    }

    public void monitorSelectStock(String code, Double highPrice, Double lowPrice, Double upPercent, Integer companyId) {
        MonitorPriceVo vo = StockContext.getInstance().getStockMonitorByCode(code, companyId);
        if (vo == null) {
            vo = new MonitorPriceVo();
        }
        vo.setCode(code);
        vo.setMonitorUpPercent(upPercent);
        vo.setMonitorHighPrice(highPrice);
        vo.setMonitorLowPrice(lowPrice);
        vo.setNotifyCount(0);
        log.debug("monitorSelectStock ret: " + JSON.toJSONString(vo));
        StockContext.getInstance().addCacheMonitor(vo, companyId);
    }

    // 富途行情推送没有名字，redis 缓存名称，并读取
    public String getStockName(String code){
        Object nameObj = redisService.getMapValue(Constant.REDIS_STOCK_NAME_KEY, code);
        if (nameObj == null) {
            String stockInfoStr = StockUtils.getStockDetail(StockUtils.getCodePrefix(code) + code);
            List<StockInfoVo> vos = StockUtils.paresTXStock(stockInfoStr);
            if (vos.size() > 0) {
                String name = vos.get(0).getName();
                redisService.putHash(Constant.REDIS_STOCK_NAME_KEY, code, name);
                return name;
            }
        } else {
            return nameObj.toString();
        }
        return null;
    }

    public void initAllStocksPlates() throws InterruptedException {
        List<Stocks> ss = getAllStocks();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        Map<String, String> retPlateMap = new HashMap<>();
        for (Stocks s : ss) {
            sb.append(s.getCode()).append(",");
            count ++;
            if (count >=198) {
                String ret = HttpUtils.doGet("http://localhost:9085/stock/api/ownerPlate?codes="+ sb.toString());
                log.info("get stock plates ret: " + ret);
                HttpResult result = JSON.parseObject(ret, HttpResult.class);
                Object d = result.getD();
                if (d == null) {
                    throw new BusinessException("获取股票所属板块失败!");
                }
                Map<String, String> data = (Map<String, String>) result.getD();
                retPlateMap.putAll(data);
                Thread.sleep(10000);
                count = 0;
                log.info("sleep 结束。。。");
                sb = new StringBuilder();
            }
        }

        for(Stocks old: ss) {
            old.setPlates(retPlateMap.get(old.getCode()));
            stocksMapper.updateByPrimaryKeySelective(old);
        }
        log.info("处理结束。。。，:" + retPlateMap.keySet().size());
    }
    public void initAllStocks(Integer market) throws InterruptedException {

    }

    public List<Stocks> getStocksByPlate(Integer market, String plateCode){
//        String service = Constant.profile.equals("prod") ? "futu-stock-service" : "stock-service-local";
//        HttpResult result = cloudService.getMethod(service, "/stock/api/getStocksByPlate?market="+market+"&plateCode=" + plateCode);
        String ret = HttpUtils.doGet("http://localhost:9085/stock/api/getStocksByPlate?market=" + market + "&plateCode=" + plateCode);
        HttpResult result = JSON.parseObject(ret, HttpResult.class);

        Object d = result.getD();
        if (d == null) {
            throw new BusinessException("获取[" + market + "]股票板块儿["+ plateCode +"]内股票列表失败!");
        }
        List<Map> rets = (List<Map>) d;
        List<Stocks> allStocks = new ArrayList<>();
        if (rets.size() > 0) {
            for(Map stock: rets) {
                String code = (String) stock.get("code");
                String name = (String) stock.get("name");
                String list_time = (String) stock.get("list_time");
                Stocks ss = new Stocks();
                ss.setCode(code);
                ss.setName(name);
                ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
                ss.setListTime(list_time);
                allStocks.add(ss);
            }
        }
        return allStocks;
    }

    public List<Stocks> getAllStocks() {
        Stocks ss = new Stocks();
        ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return stocksMapper.selectMany(ss);
    }

    public void updateStocks(Stocks ss){
        stocksMapper.updateByPrimaryKeySelective(ss);
    }

    public void addNewStocks(String code) {
        Stocks ss = new Stocks();
        ss.setCode(code);
        ss.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Stocks> ssList = stocksMapper.selectMany(ss);
        if (ssList.size() > 0) {
            throw new BusinessException("股票已存在");
        }
        String preCode = StockUtils.getCodePrefix(code) + code;
        String ret = StockUtils.getStockDetail(preCode);
        List<StockInfoVo> vos = StockUtils.paresTXStock(ret);
        StockInfoVo vo = vos.get(0);

        ss.setCode(code);
        ss.setName(vo.getName());
        stocksMapper.insert(ss);
    }
}
