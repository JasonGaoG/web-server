package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class PolicySelHighLowStatisVo {
    private Integer upCount = 0; // 上涨通知个数
    private Integer upProfitCount = 0; // 上涨通知中正收益个数
    private Double upProfitCountRate = 0D; // 上涨通知正收益个数占比  保留两位小数
    private Double downProfitCountRate = 0D; // 下跌通知正收益个数占比  保留两位小数
    private Integer downProfitCount = 0; // 下跌通知正收益个数
    private Integer downCount = 0; // 下跌通知股票个数

    private Double upProfit = 0D; // 上涨的股票的总的收益 例如买入10000块钱左右的股票
    private Double downProfit = 0D; // 下跌，同上
    private Double upProfitRate = 0D; // 上涨的股票的总的收益率 例如买入10000块钱左右的股票
    private Double downProfitRate = 0D; // 下跌同上

}
