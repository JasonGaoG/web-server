package com.sunlight.portal.runner;

import com.sunlight.portal.socketserver.server.Server;
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
@Order(1)
@Component
public class StartRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        initSocketServer();
    }

    private void initSocketServer() {
        try {
            log.info("------ start init socket server -----");
            Server.getInstance().startServer();
            log.info("------ end init socket server -----");
        } catch (Exception e) {
            log.error("start runner initGlobalData exception:", e);
        }
    }
}
