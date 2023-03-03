package com.sunlight.blc.interceptor;

import com.sunlight.blc.annotation.Authorization;
import com.sunlight.blc.constant.ConstParam;
import com.sunlight.blc.constant.PermissionClassEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @description 登录验证拦截器
 * @date 2018/11/28
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("登录校验......");
        log.info(request.getRequestURI());
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authorization authorizationAno = method.getAnnotation(Authorization.class);
        if (authorizationAno != null) {
            PermissionClassEnum[] autho = authorizationAno.autho();
            // 表示没有权限限制
            if (autho.length == 0) {
                return true;
            }
            // 获取当前登录用户及其权限
            String authorization = request.getHeader(ConstParam.AUTHORIZATION);
            log.info("authorization：{}", authorization);
            try {
                return true;
            } catch (Exception e) {
                log.error("login LoginInterceptor",e);
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(200);
                return false;
            }
        }
        return true;
    }

}
