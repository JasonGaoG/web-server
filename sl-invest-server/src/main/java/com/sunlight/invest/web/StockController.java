package com.sunlight.invest.web;

import com.sunlight.common.annotation.Authorization;
import com.sunlight.common.constant.PermissionClassEnum;
import com.sunlight.common.exception.BusinessException;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.invest.context.StockContext;
import com.sunlight.invest.model.SelStock;
import com.sunlight.invest.service.ProfitService;
import com.sunlight.invest.service.RedisService;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.utils.StockMonitorNotifyUtils;
import com.sunlight.invest.vo.SelStockVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private ProfitService profitService;

    @Resource
    private StockService stockService;

    @Resource
    private RedisService redisService;

    @PostMapping("/addStock")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult addSelectStock(HttpServletRequest request, @RequestParam String code) {
        try {
            Integer companyId = null;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            StockInfoVo vo = stockService.addSelStock(code, companyId);
            return HttpResult.ok("操作成功",vo);
        } catch (Exception e) {
            log.error("add stock exception: ", e);
            return HttpResult.error("添加失败!");
        }
    }

    @PostMapping("/deleteStock")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult deleteSelectStock(HttpServletRequest request, @RequestParam String code) {
        try {
            Integer companyId = null;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            SelStock selStock = stockService.getSelStocksByCode(code, companyId);
            if (selStock == null) {
                return HttpResult.error("自选股不存在!");
            }
            stockService.deleteSelStock(selStock.getId(), code);
            return HttpResult.ok("删除成功");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("delete stock exception: ", e);
            return HttpResult.error("删除失败!");
        }
    }

    /**
     * 监控股票价格
     * @param highPrice 监控的高价格 lowPrice 监控的低价格
     * @return http result
     */
    @PostMapping("/monitorStock")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult monitorSelectStock(HttpServletRequest request, String code, Double highPrice, Double lowPrice, Double upPercent) {
        try {
            Integer companyId = null;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            if (highPrice != null && highPrice <= 0) {
                highPrice = null;
            }
            if (lowPrice != null && lowPrice <= 0) {
                lowPrice = null;
            }
            if (highPrice == null && lowPrice == null && (upPercent == null || upPercent == 0)) {
                return HttpResult.error("请输入合理监控价格！");
            }

            stockService.monitorSelectStock(code, highPrice, lowPrice, upPercent, companyId);
            return HttpResult.ok("添加监控成功");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("add sel stock exception: ", e);
            return HttpResult.error("添加监控失败!");
        }
    }

    /**
     * 批量获取股票信息
     * @param code   600009,002234  逗号隔开的多个股票代码
     */
    @GetMapping("/getStockDetail")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult getStockDetail(HttpServletRequest request, String code){
        try {
            Integer companyId = null;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            List<StockInfoVo> rets = stockService.getStockDetail(code, companyId);
            if (rets != null) {
                return HttpResult.ok("操作成功",rets);
            }
            return HttpResult.error("获取失败");
        } catch (Exception e) {
            log.error("获取股票详情失败：", e);
            return HttpResult.error("获取失败");
        }
    }

    @GetMapping("/getSelStockList")
    @Authorization(autho = {PermissionClassEnum.ADMIN, PermissionClassEnum.INVEST_MANAGER})
    public HttpResult getSelStockList(HttpServletRequest request){
        try {
            Integer companyId = null;
            Object companyIdObj = request.getAttribute("companyId");
            if (companyIdObj != null) {
                companyId = Integer.parseInt(companyIdObj.toString());
            }
            List<SelStockVo> rets = stockService.getSelStocks(companyId);
            return HttpResult.ok("操作成功",rets);
        } catch (Exception e) {
            log.error("查询自选失败!", e);
            return HttpResult.error("获取失败!");
        }
    }

    /**
     * futu 。
     * @param infos list
     * @return
     */
    @PostMapping("/syncStockInfo")
    public HttpResult syncStockInfo(@RequestBody List<StockInfoVo> infos) {
        try {
            if (infos.size() > 0) {
                StockContext.getInstance().updateCacheFromFutu(infos);
            }
            return HttpResult.ok("操作成功","ok");
        } catch (Exception e) {
            log.error("add stock exception: ", e);
            return HttpResult.error("同步失败!");
        }
    }

    @PostMapping("/buyTips")
    public HttpResult buyTips(@RequestBody StockInfoVo stock) {
        try {
            String message = "股神推荐购买股票：" + stock.getName() + ", 代码：" + stock.getCode()
                    + ", 推荐价格： " + stock.getCurrentPrice();
            StockMonitorNotifyUtils.sendNotify(message, null);
            return HttpResult.ok("操作成功","ok");
        } catch (Exception e) {
            log.error("add stock exception: ", e);
            return HttpResult.error("同步失败!");
        }
    }

    /**
     * 添加新股 到 stocks
     * @param code 股票代码 单个
     */
    @GetMapping("/addNewStocks")
    public HttpResult addNewStocks(String code) {
        try {
            stockService.addNewStocks(code);
            return HttpResult.ok("操作成功","ok");
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return HttpResult.error(e.getMessage());
            }
            log.error("add stock exception: ", e);
            return HttpResult.error("添加失败!");
        }
    }

}
