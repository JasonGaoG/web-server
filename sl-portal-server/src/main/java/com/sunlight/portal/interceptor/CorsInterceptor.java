package com.sunlight.portal.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 跨域拦截器 相当于一个Filter拦截器，但是这个颗粒度更细
 * @author dzfking007 (<a href="mailto:dzfking007@163.com">dzfking007@163.com</a>)
 * 2018/5/14 20:08
 */
@Slf4j
@Component
public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("跨域过滤URL:{}",request.getRequestURL());
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Host"));
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "100");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Requested-With, XHR, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return super.preHandle(request, response, handler);
    }
}
