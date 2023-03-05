package com.sunlight.invest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * discover 注册服务
 * feign 调用服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.sunlight.invest.dao")
@ComponentScan(value="com.sunlight.invest.*")
@EnableFeignClients
public class InvestServerApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(InvestServerApplication.class, args);
    }

}