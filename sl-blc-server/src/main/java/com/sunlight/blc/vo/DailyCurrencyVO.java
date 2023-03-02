package com.sunlight.blc.vo;

import com.sunlight.blc.model.DailyCurrency;
import lombok.Data;

/**
 *
 */
@Data
public class DailyCurrencyVO {
    /**
     *
     */
    private Integer id;

    /**
     *   货币类型
     *
     */
    private String currency;

    /**
     *   数量
     *
     */
    private Double amount;

    /**
     *   统计每天的收益id
     *
     */
    private Integer dailyProfitId;

    /**
     *   备注
     *
     */
    private String remarks;

    public DailyCurrencyVO(){}
    public DailyCurrencyVO(DailyCurrency d){
        this.id = d.getId();
        this.amount = d.getAmount();
        this.currency = d.getCurrency();
        this.dailyProfitId = d.getDailyProfitId();
        this.remarks = d.getRemarks();
    }
}