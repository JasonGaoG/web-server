package com.sunlight.blc.binance;

import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.impl.WebsocketStreamClientImpl;
import com.sunlight.common.utils.HttpUtils;

import java.util.Calendar;

public class BinanceContext {

    private BinanceContext(){}
    private static final BinanceContext context = new BinanceContext();

    private static final Calendar calendar = Calendar.getInstance();
    private static Double currentPrice = 2300D;

    public static BinanceContext getInstance() {
        return context;
    }

    public void subPrice() {
        WebsocketStreamClientImpl wsStreamClient = new WebsocketStreamClientImpl(); // defaults to live exchange unless stated.

        int streamID1 = wsStreamClient.tradeStream("btcusdt",((event) -> {
            JSONObject obj = JSONObject.parseObject(event);
            Double price = obj.getDouble("p");
            float percent = (float)(Math.round((price - currentPrice)*100 / currentPrice))/100;
            if (percent > 3) {
                HttpUtils.sendDingMessage("btc价格变动超过3个点，当前价格：" + price + ",当前涨幅：" + percent);
                currentPrice = price;
            }
            if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
                // 每晚12点重置当前价格
                currentPrice = price;
            }
        }));
    }
}
