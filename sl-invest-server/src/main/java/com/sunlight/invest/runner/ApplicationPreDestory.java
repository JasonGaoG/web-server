package com.sunlight.invest.runner;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author Administrator
 * @description 项目停止时候运行
 * @date 2019/4/4
 */
@Component
public class ApplicationPreDestory {

    @PreDestroy
    public void run(){
    }
}
