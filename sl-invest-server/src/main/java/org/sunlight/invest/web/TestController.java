package org.sunlight.invest.web;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sunlight.common.vo.HttpResult;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @GetMapping("/get")
    public HttpResult get() {
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(useLocalCache);
        return result;
    }
}
