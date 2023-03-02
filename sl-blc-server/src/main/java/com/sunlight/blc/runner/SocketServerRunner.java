package com.sunlight.blc.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @package com.sunlight.blc.runner
 * @description 启动执行方法
 * @date 2019/4/1
 */
@Slf4j
@Order(2)
@Component
public class SocketServerRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("------ start init socket server -----");
        log.info("------ end init socket server -----");
    }
}
