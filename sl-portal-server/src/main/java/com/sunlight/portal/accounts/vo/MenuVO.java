package com.sunlight.portal.accounts.vo;

import com.sunlight.portal.accounts.model.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuVO {

    private Integer id;

    private String menuCode;

    private String menuName;

    private String menuUrl;

    private String parentCode;

    private String remarks;

    private String icon;

    private List<MenuVO> children;

    public MenuVO(){}
    public MenuVO(Menu menu){
        this.id = menu.getId();
        this.icon = menu.getIcon();
        this.parentCode = menu.getParentCode();
        this.menuCode = menu.getMenuCode();
        this.menuName = menu.getMenuName();
        this.menuUrl = menu.getMenuUrl();
        this.remarks = menu.getRemarks();
    }
}
