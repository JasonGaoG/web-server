package com.sunlight.invest;

import com.sunlight.invest.interceptor.CorsInterceptor;
import com.sunlight.invest.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author jason
 * 2018/5/14 18:13
 * @description 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private CorsInterceptor corsInterceptor;//跨域过滤

    @Resource
    private LoginInterceptor loginInterceptor;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${export.file.base.path}")
    private String fileExportPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
            .excludePathPatterns("/login/loginUser", "/test/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**");
    }
}
