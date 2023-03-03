package com.sunlight.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sunlight.portal.accounts.dao")
@ComponentScan(value="com.sunlight.portal.*")
@EnableDiscoveryClient
public class PortalApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(PortalApplication.class, args);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}