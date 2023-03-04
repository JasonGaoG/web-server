package com.sunlight.invest.vo;

import com.sunlight.invest.model.PolicySelHighLowNotify;
import lombok.Data;

@Data
public class PolicySelHighLowNotifyVo {

    private Integer id;
    private String code;
    private String name;
    // 30 天内价格列表
    private String prices;
    /**
     *   最高还是最低的通知类型
     */
    private Integer notifyType;

    /**
     *   通知时的价格
     */
    private Double notifyPrice;

    /**
     *   是否买入
     */
    private Integer hasBuy;

    /**
     *   买入价格
     */
    private Double buyPrice;

    /**
     *   是否卖出
     */
    private Integer hasSell;

    /**
     *   卖出价格
     */
    private Double sellPrice;

    /**
     *   收益
     */
    private Double profit;

    /**
     *   30天内最好收益
     */
    private Double bestProfit;

    /**
     *   30天内最差收益
     */
    private Double badProfit;

    private String remarks;


    /**
     *   记录日期
     */
    private String notifyDate;

    // 买入日期
    private String buyDate;

    /**
     *   卖出日期
     */
    private String sellDate;

    /**
     *   30日内最高日期,  自行计算
     */
    private String highEstDate;

    /**
     *   30日内最低日期，  自行计算
     */
    private String lowEstDate;

    private Double lowEstPrice;
    private Double highEstPrice;

    private Integer days;

    public PolicySelHighLowNotifyVo(){}
    public PolicySelHighLowNotifyVo(PolicySelHighLowNotify notify){
        this.id = notify.getId();
        this.badProfit = notify.getBadProfit();
        this.code = notify.getCode();
        this.name = notify.getName();
        this.notifyType = notify.getNotifyType();
        this.notifyPrice = notify.getNotifyPrice();
        this.bestProfit = notify.getBestProfit();
        this.buyPrice = notify.getBuyPrice();
        this.hasBuy = notify.getHasBuy();
        this.hasSell = notify.getHasSell();
        this.notifyDate = notify.getNotifyDate();
        this.prices = notify.getPrices();
        this.profit = notify.getProfit();
        this.remarks = notify.getRemarks();
        this.sellPrice = notify.getSellPrice();
        this.buyDate = notify.getBuyDate();
        this.sellDate = notify.getSellDate();
        this.highEstDate = notify.getHighEstDate();
        this.lowEstDate = notify.getLowEstDate();
        this.highEstPrice = notify.getHighEstPrice();
        this.lowEstPrice = notify.getLowEstPrice();
        this.days = notify.getDays();
    }
}