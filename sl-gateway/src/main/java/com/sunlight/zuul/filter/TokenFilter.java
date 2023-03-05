package com.sunlight.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sunlight.common.utils.TokenUtils;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.zuul.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.sunlight.common.constant.CommonConstant.USER_LOGIN_REDIS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RefreshScope
public class TokenFilter extends ZuulFilter {

    @Resource
    private RedisService redisService;

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
        String whiteList = "";
        if(whiteList.contains(requestURI)){
            response.setHeader("Access-Control-Expose-Headers",
                    "Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        String token = request.getHeader(AUTHORIZATION);
        Map<String, String> claims = TokenUtils.parseToken(token);
        if (claims != null) {
            String tokenCache = redisService.getStringValue(USER_LOGIN_REDIS + claims.get("userId"));
            if (token.equals(tokenCache)) {
                requestContext.setSendZuulResponse(true);// 对该请求进行路由
                requestContext.setResponseStatusCode(200);
                requestContext.set("isSuccess", true);
                return null;
            }
        }
        requestContext.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
        requestContext.setResponseStatusCode(401);// 返回错误码
        requestContext.setResponseBody(JSON.toJSONString(HttpResult.error(401, "用户未登录")));// 返回错误内容
        requestContext.set("isSuccess", false);
        return null;
    }
}
