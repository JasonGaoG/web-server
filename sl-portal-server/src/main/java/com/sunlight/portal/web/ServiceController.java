package com.sunlight.portal.web;

import com.sunlight.common.vo.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @GetMapping("/status")
    public HttpResult getServerStatus(String serverName) {
        try {
            String path = System.getProperty("user.dir");
            return HttpResult.ok("ok",path);
        } catch (Exception e) {
            return HttpResult.error("获取服务状态失败");
        }
    }
}
