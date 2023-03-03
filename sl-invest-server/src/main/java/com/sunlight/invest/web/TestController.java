package com.sunlight.invest.web;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.sunlight.invest.feigns.NacosPortalProviderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sunlight.common.vo.HttpResult;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    @Value(value = "${useLocalCache:false}")
    private boolean useLocalCache;

    @Value(value = "${whiteList}")
    private String whiteList;

    @Resource
    private NacosPortalProviderClient providerClient;

    @GetMapping("/get")
    public HttpResult get() {
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(whiteList);
        return result;
    }

    @GetMapping("/get22")
    public HttpResult get222() {
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(useLocalCache);
        return result;
    }

    @GetMapping("/get2")
    public HttpResult feignTest(Integer userId) {
        String ret = providerClient.testGet(userId);
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(ret);
        return result;
    }
}
