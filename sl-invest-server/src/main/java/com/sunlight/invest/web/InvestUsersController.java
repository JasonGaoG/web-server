package com.sunlight.invest.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.invest.service.InvestUserUService;
import com.sunlight.invest.vo.HttpResult;
import com.sunlight.invest.vo.InvestUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class InvestUsersController {

    @Resource
    private InvestUserUService investUserUService;

    @GetMapping(value = "/getInvestUser")
    public HttpResult getInvestUser(Integer userId){
        log.info("userId: " + userId);
        InvestUserVo uu = investUserUService.getInvestUser(userId);
        if (uu!= null) {
            return new HttpResult(uu);
        }

        return new HttpResult("-1");
    }

    @GetMapping(value = "/getUsers")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult getUsers(HttpServletRequest request, Integer page, Integer pageSize){
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 20;
            }

            int companyId = -1;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            log.info("get users page: {}, pageSize:{}, companyId: {}", page, pageSize, companyId);

            List<InvestUserVo> uu = investUserUService.getUsers(page, pageSize, companyId);
            return new HttpResult(uu);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error("获取用户列表失败");
        }

    }

    @PostMapping(value = "/addOrUpdateUser")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult getUsers(HttpServletRequest request, @RequestBody InvestUserVo userVo){
        try {
            if (userVo.getCompanyId() == null || userVo.getCompanyId() == 0) {
                Object companyId = request.getAttribute("companyId");
                if (companyId != null) {
                    userVo.setCompanyId(Integer.parseInt(companyId.toString()));
                }
            }
            investUserUService.addOrUpdateUser(userVo);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error("操作失败!");
        }
    }

    @PostMapping(value = "/delete")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult deleteInvestUser(Integer userId){
        try {
            investUserUService.deleteInvestUser(userId);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            return HttpResult.error("操作失败!");
        }
    }
}
