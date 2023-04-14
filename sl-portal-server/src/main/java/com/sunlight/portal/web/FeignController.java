package com.sunlight.portal.web;

import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.UserService;
import com.sunlight.portal.accounts.vo.UserVO;
import com.sunlight.portal.socketserver.constant.SocketContext;
import com.sunlight.portal.socketserver.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/feign/")
@Slf4j
public class FeignController {

    @Resource
    private UserService userService;

    @GetMapping("/getById")
    public UserVO getById(Integer userId){
        log.info("getUserById: " + userId);
        try {
            return userService.get(userId);
        } catch (Exception e) {
            log.error("error", e);
        }
        return null;
    }

    @PostMapping("/pushEvent")
    public HttpResult pushEvent(String event, Object data){
        try {
            Server.getInstance().broadCastEvent(event, data);
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.ok("广播成功.");
    }
}
