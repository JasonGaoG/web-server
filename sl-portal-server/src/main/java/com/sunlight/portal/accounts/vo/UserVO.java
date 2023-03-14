package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.User;
import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String userName;
    private String password;
    private String userRoleCode;
    private String userRoleName;
    private String remarks;
    private Integer companyId;
    private String companyName;
}
