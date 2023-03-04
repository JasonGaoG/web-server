package com.sunlight.invest.web;

import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.model.CompositeIndex;
import com.sunlight.invest.service.PolicyLimitService;
import com.sunlight.invest.service.PolicyPriceService;
import com.sunlight.invest.service.PolicyService;
import com.sunlight.invest.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Resource
    private PolicyLimitService policyLimitService;

    @Resource
    private PolicyPriceService policyPriceService;

    @Resource
    private PolicyService policyService;

    /**
     *  涨跌停交易监控数据 date: yyyy-MM-dd
     */
    @GetMapping("/getPolicyLimit")
    public HttpResult getPolicyLimit(String date){
        try {
            List<PolicyLimitVo> ret = policyLimitService.getPolicyLimitByDate(date);
            return new HttpResult(ret);
        } catch (Exception e) {
            log.error("get policy limit exception: ", e);
        }
        return HttpResult.error("获取涨跌停策略数据失败.");
    }

    @GetMapping("/exportPolicyLimitExcel")
    public HttpResult exportPolicyLimitExcel(HttpServletResponse response, String date){
        try {
            File xlsFile = policyLimitService.exportPolicyLimitExcel(date);
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("Content-Disposition", "attachment; filename=" + xlsFile.getName());
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Expires", "0");
            response.addHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.addHeader("Last-Modified", new Date().toString());
            response.addHeader("ETag", String.valueOf(System.currentTimeMillis()));
            InputStream fis = new FileInputStream(xlsFile);
            OutputStream os = response.getOutputStream();
            byte[] bis = new byte[1024];
            while(-1 != fis.read(bis)){
                os.write(bis);
            }
            return HttpResult.ok("导出成功。。");
        } catch (Exception e) {
            log.error("get policy limit exception: ", e);
        }
        return HttpResult.error("导出excel 失败。。");
    }

    @GetMapping("/searchPolicyPrice")
    public HttpResult getPolicyPrice(Integer type, String date,Integer page, Integer pageSize) {
        if (StringUtils.isBlank(date)) {
            date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        }
        if (type == null) {
            type = 0;
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 1;
        }
        try {
            List<PolicyPriceVo> vos = policyPriceService.searchPolicyPrices(type, date, page, pageSize);
            int total = policyPriceService.count(type, date);
            Map<String, Object> rets = new HashMap<>();
            rets.put("total", total);
            rets.put("list", vos);
            return new HttpResult(rets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("查询失败..");
    }

    /**
     *
     * @param date 通知日期 查询30天内的
     * @param type 通知类型 0 上涨 1 下跌
     */
    @GetMapping("/getPolicySelHighLow")
    public HttpResult getPolicySelHighLow(String date, Integer type){
        try {
            List<PolicySelHighLowNotifyVo> rets = policyService.searchSelHighLowNotify(date, type);
            return new HttpResult(rets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("查询失败..");
    }

    @GetMapping("/statisPolicySelHighLow")
    public HttpResult statisPolicySelHighLow(String date){
        if (StringUtils.isBlank(date)) {
            date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
        }
        try {
            PolicySelHighLowStatisVo vo = policyService.statisPolicySelHighLow(date);
            return new HttpResult(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("查询失败..");
    }

    /**
     *
     * @param id id
     * @param price 价格
     * @param type 0 buy, 1 sell
     */
    @PostMapping("/updatePolicySelHighLow")
    public HttpResult updatePolicySelHighLow(Integer id, Double price, Integer type){
        try {
            policyService.updatePolicySelHighLowInfo(id, price, type);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            e.printStackTrace();
        }
        return HttpResult.error("操作失败..");
    }

    @DeleteMapping("/deletePolicySelHighLow")
    public HttpResult deletePolicySelHighLow(Integer id){
        try {
            policyService.deletePolicySelHighLowInfo(id);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            e.printStackTrace();
        }
        return HttpResult.error("操作失败..");
    }

    @GetMapping("/getCompositeIndex")
    public HttpResult getCompositeIndex(String date){
        try {
            List<CompositeIndex> rets = policyService.getCompositeIndex(date);
            return HttpResult.ok("操作成功");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            e.printStackTrace();
        }
        return HttpResult.error("操作失败..");
    }
}
