package com.sunlight.blc.binance;

import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.impl.WebsocketStreamClientImpl;
import com.sunlight.common.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;

public class BinanceContext {

    @Value("${readBinanceKey}")
    private String readKey;

    @Value("${readBinanceSecret}")
    private String readSecret;

    private BinanceContext(){}
    private static final BinanceContext context = new BinanceContext();

    private static final Calendar calendar = Calendar.getInstance();
    private static Double currentPrice = 2300D;

    public static BinanceContext getInstance() {
        return context;
    }
}
