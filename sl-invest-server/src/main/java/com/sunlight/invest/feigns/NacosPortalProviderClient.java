package com.sunlight.invest.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("portal-service")
@RequestMapping("portal")
public interface NacosPortalProviderClient {

    @GetMapping("/feign/getById")
    public String testGet(@RequestParam("userId") Integer userId);
}
