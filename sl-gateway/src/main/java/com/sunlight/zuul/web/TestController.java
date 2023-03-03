package com.sunlight.zuul.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/gateway")
@RefreshScope
public class TestController {

    @Value(value = "${whiteList}")
    private String whiteList;

    @GetMapping("/get")
    public String publishConfig() {
        return whiteList;
    }
}
