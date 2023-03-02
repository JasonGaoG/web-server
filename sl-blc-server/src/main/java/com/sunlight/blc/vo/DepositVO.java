package com.sunlight.blc.vo;

import lombok.Data;

@Data
public class DepositVO {

    private Integer id;

    /**
     */
    private Long refId;

    private String account;

    /**
     *  状态
     */
    private String status;

    /**
     */
    private Double amount;

    /**
     *
     */
    private String createTime;

    /**
     *
     */
    private String updateTime;

    /**
     *
     */
    private String remarks;

    private String symbol;

}
