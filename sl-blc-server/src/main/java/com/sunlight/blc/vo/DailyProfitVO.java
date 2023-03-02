package com.sunlight.blc.vo;

import lombok.Data;


@Data
public class DailyProfitVO {

    private Integer id;

    /**
     * 8017 账户btc 数量
     */
    private Double accountXvgBtc;

    private Double accountXvgBtcIncrease;

    /**
     * 9884 btc 数量
     */
    private Double accountDcrBtc;

    private Double accountDcrBtcIncrease;

    /**
     * 两个账户总计btc 输了
     */
    private Double totalBtc;

    private Double totalBtcIncrease;

    private String day;

    private Double btcPrice;

    /**
     *
     */
    private String remarks;

}
