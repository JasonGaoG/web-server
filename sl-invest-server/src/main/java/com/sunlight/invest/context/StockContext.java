package com.sunlight.invest.context;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.constant.CometEvent;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.policy.impl.PolicyPriceMonitorHandler;
import com.sunlight.invest.policy.impl.SelStockHighLowHandler;
import com.sunlight.invest.service.RedisService;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.utils.StockUtils;
import com.sunlight.invest.vo.MonitorPriceVo;
import com.sunlight.invest.vo.SelStockVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 股票数据缓存
 */
@Slf4j
@Component
public class StockContext {

    @Resource
    private RedisService redisService;
    @Resource
    private StockService stockService;

    private static final Object lock = new Object();
    private StockContext(){}
    private static StockContext instance;
    public static StockContext getInstance (){
        if (instance == null) {
            instance = new StockContext();
        }
        return instance;
    }

    public StockInfoVo getStockInfoByCode(String code) {
        Object strObj = redisService.getMapValue(Constant.REDIS_STOCK_INFO_KEY, code);
        if(strObj != null) {
            return JSON.parseObject(strObj.toString(), StockInfoVo.class);
        }
        return null;
    }

    public MonitorPriceVo getStockMonitorByCode(String code, Integer companyId) {
        Object strObj = redisService.getMapValue(Constant.REDIS_STOCK_MONITOR + code, companyId + "");
        if(strObj != null) {
            return JSON.parseObject(strObj.toString(), MonitorPriceVo.class);
        }
        return null;
    }

    public void addCacheSelStockList(List<StockInfoVo> vos){
        Map<String, String> data = new HashMap<>();
        for(StockInfoVo vo: vos) {
            data.put(vo.getCode(),JSON.toJSONString(vo));
        }
        redisService.setMap(Constant.REDIS_STOCK_INFO_KEY, data);
    }

    /**
     * futu 推送信息更新
     * futu 个股一个个推送的。。
     * futu 信息缺失： name， 订阅情况
     */
    public void updateCacheFromFutu(List<StockInfoVo> lists){
        log.info("updateCacheFromFutu:" + lists.size());
        synchronized (lock) {
            Map<String, String> ret = new HashMap<>();
            for(StockInfoVo newVo: lists) {
                Object oldStr = redisService.getMapValue(Constant.REDIS_STOCK_INFO_KEY, newVo.getCode());
                if (oldStr == null) {
                    Object name = redisService.getMapValue(Constant.REDIS_STOCK_NAME_KEY,newVo.getCode());
                    if (name != null) {
                        ret.put(newVo.getCode(), JSON.toJSONString(newVo));
                        newVo.setName(name.toString());
                    }
                } else {
                    StockInfoVo old = JSON.parseObject(oldStr.toString(), StockInfoVo.class);
                    newVo.setName(old.getName());
                    ret.put(newVo.getCode(), JSON.toJSONString(newVo));
                }
            }

            redisService.setMap(Constant.REDIS_STOCK_INFO_KEY, ret);
            // 监听价格通知到企信
            PolicyHandler handler = new PolicyPriceMonitorHandler();
            handler.handle(lists);
            // 实时价格推送到web 页面
            // web socket推送
        }
    }
    /**
     * 定时更新推送
     */
    public void updateCache(){
        synchronized (lock) {
            List<SelStockVo> selStocks = stockService.getSelStocks(null);
            StringBuilder retkey = new StringBuilder();
            for(SelStockVo selStock: selStocks) {
                retkey.append(StockUtils.getCodePrefix(selStock.getCode())).
                        append(selStock.getCode()).append(",");
            }
            String codes = retkey.toString().replaceAll(",,",",");
            if (codes.endsWith(",")) {
                codes = codes.substring(0, codes.length() - 1);
            }
            log.info("同步股票信息，codes：" + codes);
            if (StringUtils.isBlank(codes)) {
                return;
            }
            String stockInfos = StockUtils.getStockDetail(codes);
            List<StockInfoVo> lists = StockUtils.paresTXStock(stockInfos);
            // 股价大涨或者大跌7个点 消息推送。
            PolicyHandler handler = new SelStockHighLowHandler();
            handler.handle(lists);
            log.info("tongbu, lists size:" + lists.size());
            if (lists.size() > 0) {
                Map<String, String> ret = new HashMap<>();
                for(StockInfoVo vo: lists) {
                    ret.put(vo.getCode(), JSON.toJSONString(vo));
                }
                // 监听价格通知到企信
                PolicyHandler handler2 = new PolicyPriceMonitorHandler();
                handler2.handle(lists);

                redisService.setMap(Constant.REDIS_STOCK_INFO_KEY, ret);
                // 推送实时价格到页面
                //  TODO 推送 到页面
            }
        }
    }

    public void deleteCacheSelStock(String code) {
        redisService.removeHash(Constant.REDIS_STOCK_INFO_KEY, code);
    }

    public void addCacheMonitor(MonitorPriceVo vo, Integer companyId) {
        redisService.putHash(Constant.REDIS_STOCK_MONITOR + vo.getCode(), companyId+"", JSON.toJSONString(vo));
    }
}
