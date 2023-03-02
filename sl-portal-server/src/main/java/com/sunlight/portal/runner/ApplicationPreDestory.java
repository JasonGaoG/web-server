package com.sunlight.portal.runner;

import com.sunlight.portal.socketserver.server.Server;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author Administrator
 * @package com.sunlight.blc.runner
 * @description 项目停止时候运行
 * @date 2019/4/4
 */
@Component
public class ApplicationPreDestory {

    @PreDestroy
    public void run(){
        Server.getInstance().stoptServer();
    }
}
