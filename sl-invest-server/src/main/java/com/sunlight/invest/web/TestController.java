package com.sunlight.invest.web;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sunlight.common.vo.HttpResult;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @NacosInjected
    private NamingService namingService;

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @GetMapping("/get")
    public HttpResult get() {
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(useLocalCache);
        return result;
    }

    @GetMapping("/get2")
    public HttpResult get(@RequestParam String serviceName) throws NacosException {
        List<Instance> ret = namingService.getAllInstances(serviceName);
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(ret);
        return result;
    }

    @GetMapping("/register")
    public HttpResult register() throws NacosException {
        namingService.registerInstance("invest-service", "127.0.0.1", 8080);
        HttpResult result = new HttpResult();
        result.setC(0);
        return result;
    }
}
