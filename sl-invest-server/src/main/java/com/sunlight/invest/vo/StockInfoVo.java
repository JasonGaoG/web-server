package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class StockInfoVo {
    private String name;
    private String code;
    private Double currentPrice; // 当前价格
    private Double yesterdayPrice; // 昨收价格
    private Double openPrice; // 今天开盘价格
    private Double upPrice; // 上涨价格
    private Double upPercent; // 上涨百分比
    private Double highestPrice; // 最高价
    private Double lowestPrice; // 最低
    private Double exchangePercent; // 换手率
    private Double limitUpPrice; // 涨停价
    private Double limitLowPrice; // 跌停价
    private String remarks; // 备注
    private Double turnOver; // 成交量

    // 监听信息
    private MonitorPriceVo monitorPriceVo;
}
