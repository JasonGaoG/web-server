package com.sunlight.blc.binance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 火币常量
 * @author jason
 */
@Component
public class BinanceConstant {

    private static String key = "McVzS4KjEhL3NeDEONvyd8bxzUpuwNjwN4OtQ9NBQP748BgRFVa6BacSFV72Ttae";
    private static String secret = "pJd3h367VnLpPCYQaTyphVBOQCfM9KseUxbCoRNP3ntmCHexcZwXokAVe9Re664m";

//    private static String key = "HurwYc8zmsT20gHfPjw9KvrDBRbw49tVOys2uilM8aSkxgQdDwPPI4gdqWkv3ohT"; // test
//    private static String secret = "0tASH0vZfW5o7LPr4apTuixoAMZ2V0KS1AQLWxMtg5WhSpSKEEg8av7wbrElkWC4"; // test

    public static String getKey() {
        return key;
    }

    @Value("${app.binance.key}")
    public void setKey(String key) {
        BinanceConstant.key = key;
    }

    public static String getSecret() {
        return secret;
    }

    @Value("${app.binance.secret}")
    public void setSecret(String secret) {
        BinanceConstant.secret = secret;
    }

    // 货币列表
    public static final String CURRENCY_BTC = "BTC";

    public static final String CURRENCY_XVG = "XVG";

    public static final String CURRENCY_DCR = "DCR";

    public static final String CURRENCY_USDT = "USDT";

    // 交易对列表
    public static final String SYMBOL_BTC_USDT = "BTCUSDT";
}
