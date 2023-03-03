package com.sunlight.portal.web;

import com.sunlight.common.vo.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ts")
public class TestController {

    @GetMapping("/get")
    public HttpResult get() {
        HttpResult result = new HttpResult();
        result.setC(0);
        result.setD(222);
        return result;
    }
}
