package com.sunlight.invest.vo;

import com.sunlight.common.utils.DateUtils;
import com.sunlight.invest.model.PolicyPrice;
import lombok.Data;

import java.util.Date;

@Data
public class PolicyPriceVo {
    private Integer id;
    private String code;
    private String name;
    private String prices;
    private Double newHigh20;
    private Double newHigh60;
    private Double newHigh90;
    private Double newHigh120;
    private Double newHighEst;
    private Double newLow20;
    private Double newLow60;
    private Double newLow90;
    private Double newLow120;
    private Double newLowEst;
    private String newHigh20Date;
    private String newHigh60Date;
    private String newHigh90Date;
    private String newHigh120Date;
    private String newHighEstDate;
    private String newLow20Date;
    private String newLow60Date;
    private String newLow90Date;
    private String newLow120Date;
    private String newLowEstDate;
    private String remarks;


    public PolicyPriceVo() {
    }

    // 第一次记录的股票
    public PolicyPriceVo(StockInfoVo vo) {
        String date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        this.code = vo.getCode();
        this.name = vo.getName();
        this.prices = vo.getCurrentPrice() +"";
        this.newHigh20 = vo.getCurrentPrice();
        this.newHigh20Date = date;
        this.newHigh60 = vo.getCurrentPrice();
        this.newHigh60Date = date;
        this.newHigh90 = vo.getCurrentPrice();
        this.newHigh90Date = date;
        this.newHighEst = vo.getCurrentPrice();
        this.newHighEstDate = date;
        this.newHigh120 = vo.getCurrentPrice();
        this.newHigh120Date = date;

        this.newLow20 = vo.getCurrentPrice();
        this.newLow20Date = date;
        this.newLow60 = vo.getCurrentPrice();
        this.newLow60Date =date;
        this.newLow90 = vo.getCurrentPrice();
        this.newLow90Date =date;
        this.newLow120 = vo.getCurrentPrice();
        this.newLow120Date = date;
        this.newLowEst = vo.getCurrentPrice();
        this.newLowEstDate = date;
    }

    public PolicyPriceVo(PolicyPrice pp) {
        this.id= pp.getId();
        this.code = pp.getCode();
        this.name = pp.getName();
        this.remarks = pp.getRemarks();
        this.prices = pp.getPrices();
        this.newHigh20 = pp.getNewHigh20();
        this.newHigh20Date = pp.getNewHigh20Date();
        this.newHigh60 = pp.getNewHigh60();
        this.newHigh60Date = pp.getNewHigh60Date();
        this.newHigh90 = pp.getNewHigh90();
        this.newHigh90Date = pp.getNewHigh90Date();
        this.newHighEst = pp.getNewHighEst();
        this.newHighEstDate = pp.getNewHighEstDate();
        this.newHigh120 = pp.getNewHigh120();
        this.newHigh120Date = pp.getNewHigh120Date();

        this.newLow20 = pp.getNewLow20();
        this.newLow20Date =pp.getNewLow20Date();
        this.newLow60 = pp.getNewLow60();
        this.newLow60Date =pp.getNewLow60Date();
        this.newLow90 = pp.getNewLow90();
        this.newLow90Date =pp.getNewLow90Date();
        this.newLow120 = pp.getNewLow120();
        this.newLow120Date =pp.getNewLow120Date();
        this.newLowEst = pp.getNewLowEst();
        this.newLowEstDate = pp.getNewLowEstDate();
    }

}
