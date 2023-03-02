package com.sunlight.blc.vo;

import lombok.Data;

@Data
public class ExchangeVO {

    private Integer id;

    private String account;

    /**
     *
     */
    private String orderId;

    /**
     *
     */
    private String createTime;

    /**
     *
     * T
     */
    private String finishedTime;

    /**
     *   交易数量
     *
     */
    private Double amount;

    /**
     *   手续费
     */
    private String fees;

    /**
     *   btc 数量
     */
    private String filledBtcAmount;

    /**
     *   备注
     *
     */
    private String remarks;

    private String symbol;

}
