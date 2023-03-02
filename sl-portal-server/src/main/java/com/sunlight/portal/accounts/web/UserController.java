package com.sunlight.portal.accounts.web;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.model.User;
import com.sunlight.portal.accounts.service.UserService;
import com.sunlight.portal.accounts.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult add(String username, String password, String userRole, Integer companyId){
        log.info("add参数：{}, {}, {}", username, password, userRole);
        try {
            userService.addUser(username, password, userRole, companyId);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("添加失败!");
    }

    @PostMapping("/updatePwd")
    @Authorization(autho = {
            PermissionClassEnum.ADMIN,
            PermissionClassEnum.BLC_MANAGER,
            PermissionClassEnum.INVEST_MANAGER})
    public HttpResult updatePwd(HttpServletRequest request, String oldPwd, String newPwd){
        log.info("updatePwd参数：{}, {}", oldPwd, newPwd);
        try {
            return HttpResult.ok("更新成功!");
        } catch (Exception e) {
            if(e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("更新失败!");
    }

    @PostMapping("/addOrUpdate")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult addOrUpdate(@RequestBody UserVO userVo){
        System.out.println(JSON.toJSONString(userVo));
        try {
            userService.addOrUpdateUser(userVo);
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
    public HttpResult delete(Integer userId){
        try {
            userService.deleteUser(userId);
            return HttpResult.ok("删除成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除失败!");
    }

    @GetMapping("/getUserList")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult getUserList(Integer page, Integer pageSize, String userRole){
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 20;
            }
            List<UserVO> rets = userService.searchUsers(page, pageSize, userRole);
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
