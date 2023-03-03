package com.sunlight.portal.accounts.service;

import com.sunlight.portal.accounts.dao.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统配置服务
 */
@Service("configsService")
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

}
