package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.ConfigsService;
import com.sunlight.portal.accounts.vo.ConfigsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统配置
 */
@RestController
@Slf4j
@RequestMapping("/api/configs")
public class ConfigsController {

    @Resource
    private ConfigsService configsService;

    @GetMapping("/getConfigs")
    public HttpResult getConfigs(String key){
        try {
            ConfigsVO configsVO = configsService.getConfig(key);
            return HttpResult.ok("获取成功!", configsVO);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }

    @PostMapping("/addUpdateConfigs")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult addUpdateConfigs(@RequestBody ConfigsVO configsVO){
        try {
            configsService.addUpdateConfigs(configsVO);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("操作失败!");
    }

    @PostMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer id){
        try {
            configsService.deleteConfigs(id);
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }
}
