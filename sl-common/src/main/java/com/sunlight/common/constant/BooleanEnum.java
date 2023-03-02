package com.sunlight.common.constant;

public enum BooleanEnum {

    /**
     * true 字符串
     */
    True("true"),

    /**
     * false 字符串
     */
    False("false");

    private BooleanEnum(String value){
        this.value = value;
    }
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
