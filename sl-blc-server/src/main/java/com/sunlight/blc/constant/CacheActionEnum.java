package com.sunlight.blc.constant;

/**
 * @author Administrator
 * @description redis 缓存操作类型
 * @date 2019/3/28
 */
public enum CacheActionEnum {
    GET("get", "查询数据"),//
    ADD("add", "新增数据"),//
    DELETE("delete", "删除"),//
    UPATE("update", "修改"),//
    REFRESH("refresh", "从数据库刷新"),//
    CLEAR("clear", "清空");//

    private String action;
    private String desc;

    private CacheActionEnum(String action, String desc) {
        this.action = action;
        this.desc = desc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
