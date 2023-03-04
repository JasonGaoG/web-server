package com.sunlight.invest.vo;

import com.sunlight.invest.model.SelStock;
import lombok.Data;

@Data
public class SelStockVo {

    private Integer id;
    private String code;
    private String name;
    private String remarks;
    private Integer isThird;
    private Integer companyId;

    public SelStockVo(){}

    public SelStockVo(SelStock selStock) {
        this.id = selStock.getId();
        this.code = selStock.getCode();
        this.name = selStock.getName();
        this.isThird = selStock.getIsThird();
        this.remarks = selStock.getRemarks();
        this.companyId = selStock.getCompanyId();
    }
}
