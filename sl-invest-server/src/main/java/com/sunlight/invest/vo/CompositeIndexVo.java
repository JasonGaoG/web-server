package com.sunlight.invest.vo;

import lombok.Data;

@Data
public class CompositeIndexVo {

    private Integer id;

    /**
     *   上证A  sh-a， 深成A  sz-a，创业板  sz-c
     *
     */
    private String market;

    private Double price;

    /**
     *   上涨下跌幅度
     *
     */
    private Double upPercent;

    /**
     */
    private String recordDate;


    private String remarks;

    /**
     *   成交量
     */
    private Double turnOver;

}