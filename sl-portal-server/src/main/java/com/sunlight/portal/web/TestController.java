package com.sunlight.portal.web;

import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ts")
public class TestController {

    @Resource
    private RedisService redisService;

    @GetMapping("/get")
    public HttpResult get() {
        String ret = redisService.getStringValue("testkey");
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(ret);
        return result;
    }
}
