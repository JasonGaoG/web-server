package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.Company;
import lombok.Data;

@Data
public class CompanyVO {

    private Integer id;
    private String name;
    private String address;
    private String pushUrl;
    private String remarks;

    public CompanyVO(){}

    public CompanyVO(Company c){
        this.id = c.getId();
        this.address = c.getAddress();
        this.name = c.getName();
        this.pushUrl = c.getPushUrl();
        this.remarks = c.getRemarks();
    }
}
