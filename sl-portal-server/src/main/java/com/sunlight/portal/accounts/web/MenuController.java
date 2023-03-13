package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.MenuService;
import com.sunlight.portal.accounts.vo.MenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/getMenus")
    public HttpResult getMenus(Integer page, Integer pageSize){
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 15;
            }
            List<MenuVO> vos = menuService.getMenuList(page, pageSize);
            Integer total = menuService.count();
            Map<String, Object> ret = new HashMap<>();
            ret.put("list", vos);
            ret.put("total", total);
            return HttpResult.ok("获取成功!", ret);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }

    @GetMapping("/getMenuTree")
    public HttpResult getMenuTree(String parentCode){
        try {
            if (StringUtils.isBlank(parentCode)) {
                parentCode = "";
            }
            List<MenuVO> menuVOS = menuService.getMenuTree(parentCode);
            return HttpResult.ok("获取菜单栏成功!", menuVOS);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("获取菜单栏失败!");
    }

    @DeleteMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer id){
        try {
            menuService.delete(id);
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @DeleteMapping("/deleteBatch")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult deleteBatch(String ids){
        try {
            List<String> idList = StringUtils.toList(ids);
            for(String id: idList) {
                menuService.delete(Integer.valueOf(id));
            }
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @PostMapping("/add")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult add(@RequestBody MenuVO menuVO){
        try {
            menuService.add(menuVO);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("添加失败!");
    }

    @PostMapping("/update")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult update(@RequestBody MenuVO menuVO){
        try {
            menuService.update(menuVO);
            return HttpResult.ok("修改成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("修改失败!");
    }
}
