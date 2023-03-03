package com.sunlight.portal.web;

import com.sunlight.portal.accounts.service.UserService;
import com.sunlight.portal.accounts.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
}
