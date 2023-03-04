package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class MonitorPriceVo {

    private String code; // 股票代码
    // 监控价格
    private Double monitorHighPrice; // 监控的高价
    private Double monitorLowPrice; // 监控的低价
    private Double monitorUpPercent; // 监控的涨跌幅
    private int notifyCount = 0; // 触发监控价格后的 通知次数
}
