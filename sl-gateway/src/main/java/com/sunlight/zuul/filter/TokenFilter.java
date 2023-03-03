package com.sunlight.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@RefreshScope
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        String requestURI = request.getRequestURI();
        //登录和注册放行
        if("sss".contains(requestURI)){
            String uuidToken = UUID.randomUUID().toString();
            response.setHeader("Access-Control-Expose-Headers",
                    "Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
            response.setHeader("token", uuidToken); // 设置响应头
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
