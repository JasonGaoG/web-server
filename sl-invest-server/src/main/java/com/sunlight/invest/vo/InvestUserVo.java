package com.sunlight.invest.vo;

import com.sunlight.invest.model.InvestUser;
import lombok.Data;

@Data
public class InvestUserVo {

    private Integer id;
    private String userName;
    private String remarks;
    private Integer companyId;

    public InvestUserVo(){}

    public InvestUserVo(InvestUser uu) {
        this.id = uu.getId();
        this.userName = uu.getUserName();
        this.remarks = uu.getRemarks();
        this.companyId = uu.getCompanyId();
    }
}
