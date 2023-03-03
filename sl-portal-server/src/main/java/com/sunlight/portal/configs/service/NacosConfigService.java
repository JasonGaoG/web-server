package com.sunlight.portal.configs.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("nacosConfigService")
public class NacosConfigService {

    public boolean publishConfig(String dataId, String group, String content) {
        try {
            Properties p = new Properties();
            p.put("serverAddr", "127.0.0.1:8848");
            ConfigService configService = NacosFactory.createConfigService(p);
            return configService.publishConfig(dataId, group, content);
        } catch (NacosException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
