package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.User;
import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String userName;
    private String password;
    private String userRoleCode;
    private String remarks;
    private Integer companyId;

    public UserVO(){}

    public UserVO(User uu){
        this.setId(uu.getId());
        this.setCompanyId(uu.getCompanyId());
        this.setUserName(uu.getUserName());
        this.setUserRoleCode(uu.getUserRoleCode());
    }
}
