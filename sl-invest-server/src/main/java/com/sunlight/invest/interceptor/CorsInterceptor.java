package com.sunlight.invest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2018/5/14 20:08
 */
@Slf4j
@Component
public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Host"));
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "100");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Requested-With, XHR, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return super.preHandle(request, response, handler);
    }
}
