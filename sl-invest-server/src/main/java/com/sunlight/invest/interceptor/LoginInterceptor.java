package com.sunlight.invest.interceptor;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.invest.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @description 登录验证拦截器
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authorization authorizationAno = method.getAnnotation(Authorization.class);
        if (authorizationAno != null) {
            PermissionClassEnum[] auth = authorizationAno.autho();
            // 表示没有权限限制
            if (auth.length == 0) {
                return true;
            }

            String authorization = request.getHeader(Constant.AUTHORIZATION);
            log.info("authorization：{}", authorization);
            return true;
        }
        return true;
    }
}
