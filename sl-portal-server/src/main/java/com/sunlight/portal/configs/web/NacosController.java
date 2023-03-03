package com.sunlight.portal.configs.web;

import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.configs.service.NacosConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/nacos")
public class NacosController {

    @Resource
    private NacosConfigService nacosConfigService;

    @GetMapping("/publish")
    public HttpResult publishConfig() {
        String content = "whiteList=" + new Date().getTime();
        boolean ret = nacosConfigService.publishConfig("invest-service.properties", "DEFAULT_GROUP", content);
        return HttpResult.ok("", ret);
    }
}
