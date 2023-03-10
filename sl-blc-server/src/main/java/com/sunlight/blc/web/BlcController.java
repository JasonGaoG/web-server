package com.sunlight.blc.web;

import com.sunlight.blc.annotation.Authorization;
import com.sunlight.blc.constant.PermissionClassEnum;
import com.sunlight.blc.service.BinanceService;
import com.sunlight.blc.vo.DailyProfitVO;
import com.sunlight.blc.vo.DepositVO;
import com.sunlight.blc.vo.ExchangeVO;
import com.sunlight.blc.vo.HttpResult;
import com.sunlight.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @package com.sunlight.blc.web
 * @description 收益交易接口 controller
 * @date 2019/3/19
 */
@Slf4j
@RequestMapping("/blc")
@RestController
public class BlcController {

    @Resource
    private BinanceService binanceService;

    /**
     * @author Administrator
     * @description 分页查询收益交易
     * @date 2019/3/22
     */
    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @GetMapping(value = "/exchange/page")
    public HttpResult pageExchange(String account, String symbol, String fromTime, String toTime, Integer page, Integer pageSize) {
        try {
            if (page == null || page <= 0) {
                page = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            long startTime = System.currentTimeMillis();
            List<ExchangeVO> ret = binanceService.searchExchanges(account, symbol, fromTime, toTime, page, pageSize);
            long searchTime = System.currentTimeMillis();
            int total = binanceService.searchExchangesTotal(account, symbol, fromTime, toTime);
            log.info("exchange query list time: " + (searchTime - startTime));
            log.info("exchange query total time:" + (System.currentTimeMillis() - searchTime));
            ModelMap map = new ModelMap();
            map.put("list", ret);
            map.put("total", total);
            return HttpResult.ok("查询成功!", map);
        } catch (Exception e) {
            log.error("获取兑换列表失败!", e);
            return HttpResult.error("获取兑换列表失败!");
        }
    }

    /**
     * @author Administrator
     * @description 分页查询收益交易
     * @date 2019/3/22
     */
    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @GetMapping(value = "/deposit/page")
    public HttpResult pageDeposit(String account, String symbol, String fromTime, String toTime, Integer page, Integer pageSize) {
        try {
            if (page == null || page <= 0) {
                page = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            long startTime = System.currentTimeMillis();
            List<DepositVO> ret = binanceService.searchDeposits(account, symbol, fromTime, toTime, page, pageSize);
            int total = binanceService.searchDepositsTotal(account, symbol, fromTime, toTime);
            log.info("deposit query time:" + (System.currentTimeMillis() - startTime));
            ModelMap map = new ModelMap();
            map.put("list", ret);
            map.put("total", total);
            return HttpResult.ok("查询成功!", map);
        } catch (Exception e) {
            log.error("获取收益列表失败!", e);
            return HttpResult.error("获取收益列表失败!");
        }
    }

    /**
     * @param currency xvg btc dcr
     * @author Administrator
     * @description 获取账户收益统计
     * @date 2019/3/22
     */
    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @GetMapping(value = "/account/get")
    public HttpResult getAccount(String accounts, String currency) {
        try {
            List<String> accountIds = StringUtils.toList(accounts);
            Map<String, Object> ret = new HashMap<>();
            for (String account : accountIds) {
                double binanceBtc = 0D;
                if (account.equalsIgnoreCase("9884")) {
                    binanceBtc = binanceService.getAccountBtcBalances();
                    ret.put(account, binanceBtc);
                }else {
                    ret.put(account, 0);
                }
            }
            return HttpResult.ok("查询成功2!", ret);
        } catch (Exception e) {
            log.error("获取账户收益统计失败!", e);
            return HttpResult.error("获取账户收益统计失败!");
        }
    }

    @Authorization(autho = {PermissionClassEnum.BLC_MANAGER, PermissionClassEnum.ADMIN})
    @GetMapping(value = "/getDailyProfit")
    public HttpResult getDailyProfit(Integer page, Integer pageSize) {
        try {
            if (page == null || page <= 0) {
                page = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            List<DailyProfitVO> ret = binanceService.getDailyProfits(page, pageSize);
            int total = binanceService.getDailyProfitsTotal();
            total -= 1;
            ModelMap map = new ModelMap();
            map.put("list", ret);
            map.put("total", total);
            return new HttpResult(map);
        } catch (Exception e) {
            log.error("获取账户收益统计失败!", e);
            return HttpResult.error("获取账户每天收益失败!");
        }
    }
}
