package com.sunlight.portal.accounts.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.portal.accounts.service.UserService;
import com.sunlight.portal.accounts.vo.UserVO;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult add(@RequestBody UserVO userVO){
        try {
            userService.addUser(userVO);
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

    @PostMapping("/update")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult update(@RequestBody UserVO userVo){
        try {
            userService.update(userVo);
            return HttpResult.ok("修改用户成功!");
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return HttpResult.error(e.getMessage());
            }
            log.error("error", e);
        }
        return HttpResult.error("修改用户失败!");
    }

    @PostMapping("/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN})
    public HttpResult delete(Integer userId){
        try {
            userService.deleteUser(userId);
            return HttpResult.ok("删除用户成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("删除用户失败!");
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
            return HttpResult.ok("查询用户成功!", rets);
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("查询用户失败!");
    }

    @GetMapping("/getById")
    public HttpResult getById(Integer userId){
        try {
            UserVO u = userService.get(userId);
            return HttpResult.ok("获取成功!", u);
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("获取失败!");
    }

    @GetMapping("/updatePwd")
    public HttpResult updatePwd(Integer userId, String newPwd, String oldPwd, String newPwdConfirm){
        try {
            if (StringUtils.isBlank(newPwd)) {
                return HttpResult.error("密码不能为空");
            }
            if (!newPwd.equals(newPwdConfirm)) {
                return HttpResult.error("两次密码输入不一致。");
            }
            userService.updatePwd(userId, newPwd, oldPwd);
            return HttpResult.ok("密码更新成功!");
        } catch (Exception e) {
            log.error("error", e);
        }
        return HttpResult.error("密码更新失败!");
    }
}
