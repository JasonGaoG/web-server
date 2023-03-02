package com.sunlight.blc.service;

import com.sunlight.blc.dao.ConfigMapper;
import com.sunlight.blc.model.Config;
import com.sunlight.common.constant.DelStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @package com.sunlight.blc.service
 * @description 矿 币 配置操作
 * @date 2019/4/16
 */
@Slf4j
@Service("configService")
public class ConfigService {

    @Resource
    private ConfigMapper configMapper;


    public void save(Config config) {
        config.setDelstatus(DelStatusEnum.UnDelete.getValue());
        configMapper.insertSelective(config);
    }

    public void update(Config config) {
        configMapper.updateByPrimaryKey(config);
    }

    public Config get(Integer id) {
        return configMapper.selectByPrimaryKey(id);
    }
    public Config getByKey(String key) {
        Config c = new Config();
        c.setKeyItem(key);
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        return configMapper.selectOne(c);
    }
}
