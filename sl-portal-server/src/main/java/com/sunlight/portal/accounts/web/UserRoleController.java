package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.UserRoleService;
import com.sunlight.portal.accounts.vo.UserRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/add")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult add(@RequestBody UserRoleVO userRoleVO){
        try {
            userRoleService.addUserRole(userRoleVO);
            return HttpResult.ok("添加角色成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("添加角色失败!");
    }

    @PostMapping("/update")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult update(@RequestBody UserRoleVO userRoleVO){
        try {
            userRoleService.update(userRoleVO);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("添加失败!");
    }

    @PostMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer roleId){
        try {
            userRoleService.deleteRoleById(roleId);
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @GetMapping("/getUserList")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult getRoleList(Integer page, Integer pageSize){
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 20;
            }
            List<UserRoleVO> rets = userRoleService.searchUserRoleList(page, pageSize);
            return HttpResult.ok("获取成功!", rets);
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }

    @GetMapping("/userRoles")
    public HttpResult getUserRoleList(){
        return HttpResult.ok("", PermissionClassEnum.toList());
    }
}
