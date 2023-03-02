package com.sunlight.blc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description 全局数据
 * @date 2018/11/28
 */
public class GlobalData {

    // 服务器ip地址
    public static String SERVER_URL = null;

    public static String HBG_SYNC_DATE = "HBGSYNCDATE";

    public static String HBG_SYNC_FROM_ID = "HBGSYNCFROMID";

    public static long timestampCorrect = 8 * 60 * 60 * 1000;

    public static String ORI_MONEY = "ORI_MONEY";

    public volatile static Map<String,Double> currencyPrice = new HashMap<>();

    public static long PRICE_COMET_TIME = System.currentTimeMillis();

    public static boolean upgrading = false;
    public static String upgradeModule = "";
}
