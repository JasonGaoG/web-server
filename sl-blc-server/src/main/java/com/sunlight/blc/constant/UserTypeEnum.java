package com.sunlight.blc.constant;

public enum UserTypeEnum {

    // 系统配置用户类型
    Judge("judge", "法官"),// 法官
    Normal("normal", "当事人"), // 当事人
    // 执行人和申请执行人信息
    Executors("executors", "执行人"), // 执行法官
    Executed("executed", "被执行人"),
    Applicant("applicant", "申请执行人"),
    Contractor("contractor", "承办人");// 案件法官

    private String type;
    private String desc;

    UserTypeEnum(String type, String desc) {
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
