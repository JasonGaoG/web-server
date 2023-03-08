package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.UserRole;
import lombok.Data;

@Data
public class UserRoleVO {

    private Integer id;

    private String roleCode;

    private String roleName;

    private String accessPoints;

    private String remarks;

    public UserRoleVO(){}

    public UserRoleVO(UserRole role) {
        this.id = role.getId();
        this.roleCode = role.getRoleCode();
        this.roleName = role.getRoleName();
        this.remarks = role.getRemarks();
        this.accessPoints = role.getAccessPoints();
    }
}
