package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统配置
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/getConfigs")
    public HttpResult getConfigs(String key){
        try {
            return HttpResult.ok("获取成功!");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }

    @PostMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer id){
        try {
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }
}
