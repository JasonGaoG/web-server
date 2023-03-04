package com.sunlight.invest.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.invest.service.InvestService;
import com.sunlight.invest.vo.HttpResult;
import com.sunlight.invest.vo.InvestUserVo;
import com.sunlight.invest.vo.ProfitVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/profit")
public class InvestController {

    @Resource
    private InvestService investService;

    @GetMapping(value = "/getInvestUser")
    public HttpResult getInvestUser(Integer userId){
        log.info("userId: " + userId);
        InvestUserVo uu = investService.getInvestUser(userId);
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

            Integer companyId = -1;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            log.info("get users page: {}, pageSize:{}, companyId: {}", page, pageSize, companyId);

            List<InvestUserVo> uu = investService.getUsers(page, pageSize, companyId);
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
            investService.addOrUpdateUser(userVo);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error("操作失败!");
        }
    }

    @PostMapping(value = "/deleteInvestUser")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult deleteInvestUser(Integer userId){
        try {
            investService.deleteInvestUser(userId);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            return HttpResult.error("操作失败!");
        }
    }

    @PostMapping("/addProfit")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult addProfit(@RequestBody ProfitVo profit) {
        try {
            investService.addOrUpdateProfit(profit);
            return HttpResult.ok("添加成功！");
        } catch (Exception e) {
            log.error("",e);
            return HttpResult.error("创建失败!");
        }
    }

    @PostMapping("/updateProfit")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult updateProfit(@RequestBody ProfitVo profit) {
        try {
            investService.addOrUpdateProfit(profit);
            return HttpResult.ok("修改成功！");
        } catch (Exception e) {
            log.error("",e);
            return HttpResult.error("修改失败!");
        }
    }

    @DeleteMapping("/deleteProfit")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult deleteProfit(Integer profitId) {
        try {
            investService.deleteProfit(profitId);
            return HttpResult.ok("删除成功！");
        } catch (Exception e) {
            log.error("",e);
            return HttpResult.error("删除失败!");
        }
    }

    @GetMapping(value = "/getProfits")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult getProfits(HttpServletRequest request, Integer page, Integer pageSize, Integer userId,
                                 String startTime, String endTime){
        log.info("get users page: {}, pageSize:{}, start: {}, end: {}",
                page, pageSize, startTime, endTime);
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }

        Integer companyId = null;
        Object companyIdObj = request.getAttribute("companyId");
        if (companyIdObj != null) {
            companyId = Integer.parseInt(companyIdObj.toString());
        }

        Double unSettled = investService.getUnSettledProfits(userId, companyId);
        List<ProfitVo> profits = investService.getProfits(userId, page, pageSize, startTime, endTime, companyId);
        int total = investService.count(userId, startTime, endTime, companyId);
        int totalProfits = 0;
        Set<String> days = new HashSet<>();
        for(ProfitVo vo : profits) {
            totalProfits += vo.getProfit();
            days.add(vo.getDate());
        }

        Map<String, Object> rets = new HashMap<>();
        rets.put("profits", profits);
        rets.put("total", total);
        rets.put("totalProfits", totalProfits);
        rets.put("unSettled", unSettled);
        rets.put("daily", Math.round(totalProfits / (days.size() == 0 ? 1D : days.size())));
        return new HttpResult(rets);
    }

    @PostMapping("settleProfit")
    public HttpResult settleProfit(@RequestBody List<Integer> ids){
        try {
            investService.settleProfit(ids);
            return HttpResult.ok("操作成功!");
        } catch (Exception e) {
            return HttpResult.error("操作失败!");
        }
    }
}
