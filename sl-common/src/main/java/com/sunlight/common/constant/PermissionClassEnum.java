package com.sunlight.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PermissionClassEnum {
    ADMIN("admin", "管理员"), // 所有权限
    BLC_MANAGER("blc_manager", "矿币管理员"),// 币信息管理员权限
    INVEST_MANAGER("invest_manager", "投资管理员"); // 股票查看权限

    private String value;
    private String label;

    PermissionClassEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }
    public String getLabel() { return label; }

    public void setValue(String value) {
        this.value = value;
    }
    public void setLabel(String label) { this.label = label; }

    public static List<Map<String, String>> toList(){
        List<Map<String, String>> ret = new ArrayList<>();
        PermissionClassEnum[] values = PermissionClassEnum.values();
        for(PermissionClassEnum p: values) {
            Map<String, String> temp = new HashMap<>();
            temp.put("value",p.getValue());
            temp.put("label", p.getLabel());
            ret.add(temp);
        }
        return ret;
    }
}
