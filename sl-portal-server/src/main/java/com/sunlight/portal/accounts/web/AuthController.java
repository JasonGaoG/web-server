package com.sunlight.portal.accounts.web;

import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @package com.sunlight.portal.accounts.web
 * @description 登录模块
 * @date 2019/3/15
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public HttpResult login(String username, String password){
        log.info("登录参数：{}, {}", username, password);
        try {
            String token = userService.login(username, password);
            if (token != null) {
                return HttpResult.ok("登录成功!", token);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("登录失败!");
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public HttpResult logout(HttpServletRequest request) {
        try {
            return HttpResult.ok("ok");
        } catch (Exception e) {
            log.error("用户注销失败!", e);
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
        }
        return HttpResult.error("用户注销失败！");
    }

    @GetMapping(value = "/verifyAuth")
    public HttpResult verifyAuth(String token) {
        try {
            return HttpResult.ok("ok");
        } catch (Exception e) {
            log.error("用户权限校验失败!", e);
            return HttpResult.error("用户权限校验失败！");
        }
    }
    /**
     * @param token token
     * @description 心跳
     */
    @RequestMapping(value = "/v1/hearbeat",method = RequestMethod.GET)
    public HttpResult hearbeat(String token) {

        try {
            return HttpResult.error("心跳刷新失败!");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            return HttpResult.error("心跳刷新失败！");
        }
    }

}
