package com.sunlight.portal.accounts.service;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.portal.accounts.vo.ConfigsVO;
import com.sunlight.portal.accounts.dao.ConfigsMapper;
import com.sunlight.portal.accounts.model.Configs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置服务
 */
@Service("configsService")
public class ConfigsService {

    @Resource
    private ConfigsMapper configsMapper;

    public ConfigsVO getConfig(String key){
        Configs configs = new Configs();
        configs.setItemKey(key);
        Configs temp = configsMapper.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(temp));
        configs.setDelstatus(DelStatusEnum.UnDelete.getValue());
        System.out.println(JSON.toJSONString(configs));
        Configs temp2 = configsMapper.selectOne(configs);
        if (temp2 != null) {
            return new ConfigsVO(temp);
        }
        return null;
    }

    public List<ConfigsVO> getConfigList(Integer page, Integer pageSize){
        List<ConfigsVO> rets = new ArrayList<>();
        Configs configs = new Configs();
        configs.setPageSize(pageSize);
        configs.setStart((page -1) * pageSize);
        configs.setDelstatus(DelStatusEnum.UnDelete.getValue());
        List<Configs> colist = configsMapper.selectMany(configs);
        if (colist != null && !colist.isEmpty()) {
            rets.add(new ConfigsVO(configs));
        }
        return rets;
    }

    public void addConfigs(String key, String value, String desc) {
        Configs c = new Configs();
        c.setItemKey(key);
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        Configs oldc = configsMapper.selectOne(c);
        if (oldc != null) {
            throw new BusinessException("配置已经存在..");
        }
        c.setItemValue(value);
        c.setItemDesc(desc);
        configsMapper.insert(c);
    }

    public void deleteConfigs(Integer id) {
        Configs c = new Configs();
        c.setId(id);
        c.setDelstatus(DelStatusEnum.Delete.getValue());
        configsMapper.updateByPrimaryKey(c);
    }

    public void addUpdateConfigs(ConfigsVO vo) {
        Configs c = new Configs();
        c.setItemDesc(vo.getItemDesc());
        c.setItemValue(vo.getItemValue());
        c.setItemKey(vo.getItemKey());
        c.setDelstatus(DelStatusEnum.UnDelete.getValue());
        if (vo.getId() != null) {
            c.setId(vo.getId());
            configsMapper.updateByPrimaryKey(c);
        } else {
            configsMapper.insertSelective(c);
        }
    }
}
