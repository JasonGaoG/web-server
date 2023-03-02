package com.sunlight.blc.constant;

public enum PlatformTypeEnum {

    MSS("mss", "流媒体"),//
    CU("cu", "监控平台");//

    private String type;
    private String desc;

    private PlatformTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
