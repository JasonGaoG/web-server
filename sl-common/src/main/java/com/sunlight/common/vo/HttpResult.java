package com.sunlight.common.vo;

import lombok.Data;

@Data
public class HttpResult {
    private Integer c;
    private String m;
    private Object d;

    public HttpResult(){}

    public static HttpResult error(String msg) {
        HttpResult ret = new HttpResult();
        ret.setC(-1);
        ret.setM(msg);
        return ret;
    }

    public static HttpResult error(Integer error, String msg) {
        HttpResult ret = new HttpResult();
        ret.setC(error);
        ret.setM(msg);
        return ret;
    }
    public static HttpResult ok(String msg) {
        HttpResult ret = new HttpResult();
        ret.setC(0);
        ret.setM(msg);
        return ret;
    }

    public static HttpResult ok(String msg, Object data) {
        HttpResult ret = new HttpResult();
        ret.setC(0);
        ret.setM(msg);
        ret.setD(data);
        return ret;
    }
}
