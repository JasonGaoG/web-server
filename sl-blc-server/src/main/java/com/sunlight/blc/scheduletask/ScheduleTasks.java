package com.sunlight.blc.scheduletask;

import com.alibaba.fastjson.JSON;
import com.sunlight.blc.constant.GlobalData;
import com.sunlight.blc.model.DailyProfit;
import com.sunlight.blc.service.BinanceService;
import com.sunlight.blc.service.ConfigService;
import com.sunlight.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 定时任务
 *
 * @author jason
 * @date 2019-05-06
 */
@Slf4j
@Component
public class ScheduleTasks {

    @Resource
    private ConfigService configService;

    @Resource
    private BinanceService binanceService;

    private Double initPrice = 1D;

    /**
     * 每天中午13点记录收益
     */
    @Scheduled(cron = "0 0 13 * * ?")
    public void recordDailyProfit() {
        log.info("记录每天收益！");
        int count = 3;
        while (count > 0) {
            try {
                // binance tongji
                double amount3 = binanceService.getAccountBtcBalances();
//                log.info("daily profit:{}, {}, {},",amount, amount2, amount3);
                log.info("daily profit:{},", amount3);
                count = 0;
                DailyProfit dp = new DailyProfit();
                dp.setAccountDcrBtc(0D);
                dp.setAccountXvgBtc(0D);
                dp.setAccountDcrBtc(amount3);
                Double price = GlobalData.currencyPrice.get("btcusdt");
                dp.setBtcPrice(price == null ? 1 : price);
                dp.setTotalBtc(dp.getAccountDcrBtc());
                binanceService.saveDailyProfit(dp);
                log.info("每天收益记录：" + JSON.toJSONString(dp));
            } catch (Exception e) {
                count--;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                log.error("记录每天收益exception：", e);
            }
        }

    }

    /**
     * 查询btc 价格，定时推送
     */
    @Scheduled(cron = "0/20 * * * * ?")
    public void monitorBtcPrice() {
        try {
            log.info("binance btc price scheduled tasks");
            Double price = binanceService.getPrice("BTCUSDT");
            log.info("binance current price: " + price + ", initPrice: " + initPrice);
            GlobalData.currencyPrice.put("btcusdt", price);
            if (initPrice == 1D) {
                initPrice = price;
                return;
            }
            float percent = (float)(Math.round((price - initPrice)*100 / initPrice))/100;
            if (percent > 3) {
                HttpUtils.sendDingMessage("btc价格变动超过3个点，当前价格：" + price + ",涨幅：" + percent);
                initPrice = price;
            }
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0 && Calendar.getInstance().get(Calendar.MINUTE) == 0) {
                // 每晚12点重置当前价格
                initPrice = price;
            }
        } catch (Exception e) {
            log.error("查询币安btc 价格 异常：", e);
        }
    }
}
