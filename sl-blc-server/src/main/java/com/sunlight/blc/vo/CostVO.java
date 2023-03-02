package com.sunlight.blc.vo;

import lombok.Data;

@Data
public class CostVO {

    private Integer id;

    /**
     *   花费数量
     *
     */
    private Double amount;

    /**
     *   时间
     */
    private String dateTime;

    /**
     *   花费详情
     *
     */
    private String details;

    /**
     *   备注
     */
    private String remarks;

    private Double balance;
}
