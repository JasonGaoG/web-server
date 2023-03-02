package com.sunlight.portal.socketserver.constant;

/**
 * @author Administrator
 * @description  socketio 推送事件类型
 * @date 2018/10/14 14:02
 */
public class CometEvent {
    //系统事件
    public static final String SYSTEM_EVENT = "system_event";

    /**
     * 行情信息。 201以上
     */
    public static final String CURRENCY_PRICE = "201";

    /**
     * 股票信息 300 以上
     */
    public static final String STOCK_INFO = "301";



}
