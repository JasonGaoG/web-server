package com.sunlight.invest.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.invest.service.ProfitService;
import com.sunlight.invest.vo.ProfitVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/profits")
public class ProfitsController {

    @Resource
    private ProfitService profitService;

    @PostMapping("/addProfit")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult addProfit(@RequestBody ProfitVo profit) {
        try {
            profitService.addOrUpdateProfit(profit);
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
            profitService.addOrUpdateProfit(profit);
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
            profitService.deleteProfit(profitId);
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

        Double unSettled = profitService.getUnSettledProfits(userId, companyId);
        List<ProfitVo> profits = profitService.getProfits(userId, page, pageSize, startTime, endTime, companyId);
        int total = profitService.count(userId, startTime, endTime, companyId);
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
        return HttpResult.ok("ok",rets);
    }

    @PostMapping("settleProfit")
    public HttpResult settleProfit(@RequestBody List<Integer> ids){
        try {
            profitService.settleProfit(ids);
            return HttpResult.ok("操作成功!");
        } catch (Exception e) {
            return HttpResult.error("操作失败!");
        }
    }
}
