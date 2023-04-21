package com.sunlight.invest.service;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.ExcelUtils;
import com.sunlight.common.utils.SpringBeanUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.PolicyLimitTypeEnum;
import com.sunlight.invest.dao.PolicyLimitMapper;
import com.sunlight.invest.model.PolicyLimit;
import com.sunlight.invest.model.Stocks;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.policy.impl.PolicyLimitHandler;
import com.sunlight.invest.policy.impl.PolicyPriceHandler;
import com.sunlight.invest.utils.StockUtils;
import com.sunlight.invest.vo.PolicyLimitVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Slf4j
@Service("policyLimitService")
public class PolicyLimitService {

    @Resource
    private PolicyLimitMapper policyLimitMapper;

    private String formatDate = "yyyy-MM-dd";

    public void addPolicyLimit(PolicyLimit pl){
        policyLimitMapper.insert(pl);
    }

    public void insertBatch(List<PolicyLimit> pls) {
        policyLimitMapper.insertBatch(pls);
    }

    public List<PolicyLimitVo> getPolicyLimitByDate(String date){
        if (StringUtils.isBlank(date)) {
            date = DateUtils.getDateString(new Date(), formatDate);
        }
        PolicyLimit pl = new PolicyLimit();
        pl.setLimitDate(date);
        pl.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<PolicyLimit> pls = policyLimitMapper.selectMany(pl);
        List<PolicyLimitVo> vos = new ArrayList<>();
        pls.forEach(p -> {
            vos.add(new PolicyLimitVo(p));
        });
        return vos;
    }

    public void statisDayInfo(List<Stocks> ss) throws InterruptedException {
        int size = ss.size();
        log.info("统计每天股票信息： " + size);
        List<Stocks> temp = new ArrayList<>();
        List<StockInfoVo> list = new ArrayList<>();
        for (int i =0; i< size; i++) {
            temp.add(ss.get(i));
            if (i % 100 == 0) {
                list.addAll(statisInfos(temp));
                temp = new ArrayList<>();
                Thread.sleep(10000); // 10 秒钟调用一次
            }
        }
        log.info("统计每天股票信息, 获取到信息的股票数量： " + list.size());
        // 涨停跌停，大涨大跌 监控
        PolicyHandler<StockInfoVo> handler = SpringBeanUtils.getBean(PolicyLimitHandler.class);
        handler.handle(list);
        // 新高新低价格监控
        PolicyHandler<StockInfoVo> handler1 = SpringBeanUtils.getBean(PolicyPriceHandler.class);
        handler1.handle(list);
    }

    public List<StockInfoVo> statisInfos(List<Stocks> stocks){
        StringBuilder codes = new StringBuilder();
        for(Stocks ss: stocks) {
            codes.append(StockUtils.getCodePrefix(ss.getCode())).append(ss.getCode()).append(",");
        }

        String result = codes.toString();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() -1);
        }
        log.info("get policylimit 查询股票信息：" + result);
        String ret = StockUtils.getStockDetail(result);
        if (ret != null) {
            return StockUtils.paresTXStock(ret);
        }
        return new ArrayList<>();
    }

    // 获取上一个交易日日期
    public String getLastLimitDate(){
        String sql = "SELECT max(limit_date) FROM policy_limit WHERE delstatus = 0 LIMIT 10;";
        Object ret = policyLimitMapper.executeCustomSql(sql);
        if (ret != null) {
            return ret.toString();
        }
        return DateUtils.getDateString(new Date(), formatDate);
    }

    public File exportPolicyLimitExcel(String date) {
        List<PolicyLimitVo> datas = getPolicyLimitByDate(date);
        List<PolicyLimitVo> upList = new ArrayList<>(); // 涨停股
        List<PolicyLimitVo> downList = new ArrayList<>(); // 跌停股
        List<PolicyLimitVo> upHighList = new ArrayList<>(); // 大幅上涨股
        List<PolicyLimitVo> downHighList = new ArrayList<>(); // 大福下跌股

        datas.forEach(d -> {
            if (d.getLimitType().equals(PolicyLimitTypeEnum.LIMIT_UP.getValue())) {
                upList.add(d);
            }
            if (d.getLimitType().equals(PolicyLimitTypeEnum.LIMIT_DOWN.getValue())){
                downList.add(d);
            }
            if (d.getLimitType().equals(PolicyLimitTypeEnum.LIMIT_5_UP.getValue())) {
                upHighList.add(d);
            }
            if (d.getLimitType().equals(PolicyLimitTypeEnum.LIMIT_5_DOWN.getValue())) {
                downHighList.add(d);
            }
        });
        Map<String, List<PolicyLimitVo>> map = new HashMap<>();
        map.put("涨停股", upList);
        map.put("跌停股", downList);
        map.put("大涨股", upHighList);
        map.put("大跌股", downHighList);
        return ExcelUtils.exportExcelFile(map, PolicyLimitVo.class, date+".xls", "/usr/local/sunlight");
    }
}
