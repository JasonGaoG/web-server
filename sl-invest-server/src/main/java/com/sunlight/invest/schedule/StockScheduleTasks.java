package com.sunlight.invest.schedule;

import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.context.PolicyContext;
import com.sunlight.invest.context.StockContext;
import com.sunlight.invest.model.Stocks;
import com.sunlight.invest.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 定时任务
 *
 * @author jason
 */
@Slf4j
@Component
public class StockScheduleTasks {

    @Resource
    private StockService stockService;

    @Resource
    private PolicyLimitService policyLimitService;

    @Resource
    private TradeDateService tradeDateService;

    @Resource
    private PolicyService policyService;

    // 早上八点重新获取股票所属板块儿。
//    @Scheduled(cron = "0 0 8 * * ?")
    public void updateStockPlates(){
        try {
            List<Stocks> allStocks = stockService.getAllStocks();
            StringBuilder builder = new StringBuilder();
            Map<String, Stocks> toUpdate = new HashMap<>();
            for(Stocks ss : allStocks) {
                if (StringUtils.isBlank(ss.getPlates())) {
                    builder.append(ss.getCode()).append(",");
                    toUpdate.put(ss.getCode(), ss);
                }
            }
            if (builder.toString().length() > 0) {
                log.info("定时获取股票板块儿为空：");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void updateIsTradeDate(){
        try {
            Constant.IS_TRADE_DATE = tradeDateService.isTradeDate(null);
            log.info("update is trade date: " + Constant.IS_TRADE_DATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 下午4点统计每天涨停股票
    @Scheduled(cron = "0 0 16 * * ?")
    public void staticStockDayInfo(){
        log.info("统计每天股票信息 start...");
        try {
            if (!Constant.IS_TRADE_DATE) {
                log.info("staticStockDayInfo is not trade date, current date is: " + DateUtils.getDateString(new Date()));
                return;
            }
            List<Stocks> ss = stockService.getAllStocks();
            policyLimitService.statisDayInfo(ss);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计每天股票信息 exception：", e);
        }

        try {
            if (!Constant.IS_TRADE_DATE) {
                log.info("statsSelHighLowNotifyInfo is not trade date, current date is: " + DateUtils.getDateString(new Date()));
                return;
            }
            policyService.updateSelHighLowNotifyInfos();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计大涨大跌股票信息 exception：", e);
        }
    }

    // 同步所有股票 code 和名称映射信息
    @Scheduled(cron = "0/10 * * * * ?")
    public void syncAllStocks() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            // 周六日不同步
            return;
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 9 ||
                calendar.get(Calendar.HOUR_OF_DAY) >= 15) {
            return; // 非交易时间不同步。。。
        }

        if (!Constant.IS_TRADE_DATE) {
            return;
        }
        try {
            log.info("同步自选股票信息~~");
            StockContext.getInstance().updateCache();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("同步自选记录error：", e);
        }
    }

    // 每天凌晨定时删除第三方自选股，
    @Scheduled(cron = "0 0 0 * * ?")
    public void delThirdStocks() {
        try {
            log.info("删除第三方自选股票~~");
            stockService.batchDeleteThirdPartSelStock();
            log.info("清理股票通知缓存~~");
            PolicyContext.getInstance().clearNotified();
            log.info("取消订阅所有股票~~");
            // ...
        } catch (Exception e) {
            log.error("删除第三方自选股票error：", e);
        }
    }

    // 每天早上九点26 清空自选个大涨大跌提示
    @Scheduled(cron = "0 26 9 * * ?")
    public void delSelStocksHighLow() {
        try {
            if (!Constant.IS_TRADE_DATE) {
                return;
            }
            PolicyContext.getInstance().clearNotified();
            // 订阅自选股
//            PolicyContext.getInstance().subSelStocks();
        } catch (Exception e) {
            log.error("清空自选个大涨大跌提示：", e);
        }
    }

    // 每天下午三点半记录大盘指数，
    @Scheduled(cron = "0 30 15 * * ?")
    public void recordCompositeIndex() {
        try {
            if (!Constant.IS_TRADE_DATE) {
                return;
            }
            log.info("每天下午三点半记录大盘指数...");
            policyService.updateCompositeIndex();
        } catch (Exception e) {
            log.error("下午三点半记录大盘指数error：", e);
        }
    }
}