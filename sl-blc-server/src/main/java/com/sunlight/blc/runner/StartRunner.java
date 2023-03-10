package com.sunlight.blc.runner;

import com.sunlight.blc.service.BinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author Administrator
 * @package com.sunlight.blc.runner
 * @description 启动执行方法
 * @date 2019/4/1
 */
@Slf4j
@Order(1)
@Component
public class StartRunner implements CommandLineRunner {

    @Resource
    private BinanceService binanceService;

    @Override
    public void run(String... args) {
        initGlobalData();
        initRedisCache();
    }

    private void initGlobalData() {
        log.error("start runner initGlobalData");

    }

    /**
     * @author Administrator
     * @description 初始化redis 缓存数据
     * @date 2019/4/15
     */
    private void initRedisCache() {
        log.info("------ start init data -----");
    }
}
