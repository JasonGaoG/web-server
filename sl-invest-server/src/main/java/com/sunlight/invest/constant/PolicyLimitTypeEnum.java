package com.sunlight.invest.constant;

public enum PolicyLimitTypeEnum {
    LIMIT_UP(0, "涨停"), // 所有权限
    LIMIT_DOWN(1, "跌停"),// 币信息管理员权限
    LIMIT_5_DOWN(2, "当天下跌超过5个点"),// 币信息管理员权限
    LIMIT_5_UP(3, "当天上涨超过5个点"); // 股票查看权限

    private int value;
    private String label;

    PolicyLimitTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }
    public String getLabel() { return label; }

    public void setValue(int value) {
        this.value = value;
    }
    public void setLabel(String label) { this.label = label; }
}
