package com.sunlight.invest.constant;

public class Constant {
    public static String EXPORT_FILE_PATH = "/Users/jason/work/document/test";

    public static String SZ_A="399106"; // 深成A指
    public static String SZ_C="399006"; // 创业板
    public static String SH_A="000001"; // 上证A指
    public static String SH_K="000688"; // 科创
    public static final Integer NOTIFY_HIGH_UP = 0;
    public static final Integer NOTIFY_LOW_DOWN = 1;

    public static Integer IS_THIRD_STOCK = 1;
    public static Integer IS_NOT_THIRD_STOCK = 0;

    public static Boolean IS_TRADE_DATE = false;

    public static final String AUTHORIZATION = "Authorization";

    public static final String REDIS_STOCK_NAME_KEY="stock_names";

    public static final String REDIS_STOCK_INFO_KEY="stock_infos";

    public static final String REDIS_STOCK_MONITOR ="stock_monitors_";

    // 涨跌 7个点通知的股票
    public static final String REDIS_STOCK_NOTIFIED ="stock_notified_";

    public static final String REDIS_COMPANY_NOTIFY_URL ="company_notify_url_";
}
