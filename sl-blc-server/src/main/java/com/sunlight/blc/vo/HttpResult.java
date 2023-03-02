package com.sunlight.blc.vo;

import lombok.Data;

@Data
public class HttpResult {
    private String c; // code
    private String m; // 错误信息
    private Object d; // 数据

    public HttpResult(){}

    public HttpResult(String c, String m, Object d) {
        this.setC(c);
        this.setD(d);
        this.setM(m);
    }

    public HttpResult(Object d) {
        this.setC("0");
        this.setD(d);
    }

    public static HttpResult errorCode(String c) {
        HttpResult ret = new HttpResult();
        ret.setC(c);
        return ret;
    }

    public static HttpResult error(String m) {
        HttpResult ret = new HttpResult();
        ret.setC("-1");
        ret.setM(m);
        return ret;
    }

    public static HttpResult ok(String m) {
        HttpResult ret = new HttpResult();
        ret.setC("0");
        ret.setM(m);
        return ret;
    }

    public static HttpResult ok(String m, Object d) {
        HttpResult ret = new HttpResult();
        ret.setC("0");
        ret.setM(m);
        ret.setD(d);
        return ret;
    }
}
