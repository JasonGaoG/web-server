package com.sunlight.invest.vo;

import com.sunlight.common.annotation.ExcelHeader;
import com.sunlight.invest.constant.PolicyLimitTypeEnum;
import com.sunlight.invest.model.PolicyLimit;
import lombok.Data;

/**
 * 涨跌停监控策略 对象
 */
@Data
public class PolicyLimitVo {

    private Integer id;
    @ExcelHeader(value = "代码")
    private String code;

    @ExcelHeader(value = "名称")
    private String name;
    @ExcelHeader(value = "所属板块")
    private String plates;
    @ExcelHeader(value = "当前价格")
    private Double currentPrice;
    @ExcelHeader(value = "最高价格")
    private Double highestPrice;
    @ExcelHeader(value = "最低价格")
    private Double lowestPrice;
    @ExcelHeader(value = "涨跌幅")
    private Double upPercent;
    private Integer limitType;
    private String limitDesc;
    @ExcelHeader(value = "天数")
    private Integer days;
    private String remarks;
    @ExcelHeader(value = "记录日期")
    private String limitDate;

    public PolicyLimitVo(){}

    public PolicyLimitVo(PolicyLimit pl){
        this.id = pl.getId();
        this.code = pl.getCode();
        this.name = pl.getName();
        this.plates = pl.getPlates();
        this.limitType = pl.getLimitType();
        this.limitDesc = PolicyLimitTypeEnum.LIMIT_UP.getValue() == pl.getLimitType() ? "涨停" : "跌停";
        this.days = pl.getDays();
        this.remarks = pl.getRemarks();
        this.limitDate = pl.getLimitDate();
        this.currentPrice = pl.getCurrentPrice();
        this.upPercent = pl.getUpPercent();
        this.highestPrice = pl.getHighestPrice();
        this.lowestPrice = pl.getLowestPrice();
    }
}
