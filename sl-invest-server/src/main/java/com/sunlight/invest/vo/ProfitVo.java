package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class ProfitVo {

    private Integer id;
    private String date;
    private Integer userId;
    private String userName;// invest user
    private String stockName;
    private Double profit;
    private String remarks;
    private Integer settled;
    private Integer companyId;
}
