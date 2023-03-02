package com.sunlight.blc.scheduletask;

import com.alibaba.fastjson.JSON;
import com.sunlight.blc.constant.GlobalData;
import com.sunlight.blc.model.DailyProfit;
import com.sunlight.blc.service.BinanceService;
import com.sunlight.blc.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
     * 出售币安dcr 每4个小时一次
     */
    @Scheduled(cron = "0 0 0/4 * * ?")
    public void sellXVGFromBinance() {
        try {
            log.info("binance scheduled tasks");
            Double xvg = binanceService.getCurrencyAmount("XVG");
            log.info("binance sell xvg ~~: " + xvg);
            if (xvg > 5000) {
                binanceService.sellCurrency("XVG", "USDT", xvg.toString().split("\\.")[0]);
            }
            
            Double usdt = binanceService.getCurrencyAmount("USDT");
            log.info("binance sell usdt: " + usdt);
            if (usdt > 50) {
                binanceService.sellCurrencyToBtc("USDT", usdt.toString().split("\\.")[0]);
            }
        } catch (Exception e) {
            log.error("出售币安xvg 异常：", e);
        }
    }
}
