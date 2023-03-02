package com.sunlight.blc.constant;

public enum CaseArrStatusEnum {

    Performed("performed", "已执行"),
    Unperform("unperform", "未执行"),//
    Performing("performing", "执行中");//

    private String status;
    private String desc;

    private CaseArrStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
