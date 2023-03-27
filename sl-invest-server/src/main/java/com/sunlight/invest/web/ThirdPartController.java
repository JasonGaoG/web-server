package com.sunlight.invest.web;

import com.sunlight.common.utils.StringUtils;
import com.sunlight.common.vo.HttpResult;
import com.sunlight.invest.model.Stocks;
import com.sunlight.invest.model.TradeDate;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.service.TradeDateService;
import com.sunlight.invest.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/third")
public class ThirdPartController {

    @Resource
    private StockService stockService;

    @Resource
    private TradeDateService tradeDateService;

    @GetMapping("/getKLines")
    public HttpResult getKLines(String code, String start, String end, Integer count){
        if (count == null) {
            count = 30;
        }
        try {
            String fullCode = StockUtils.getCodePrefix(code) + code;
            String lines = StockUtils.getStockHistoryLines(fullCode, start, end, count);
            return HttpResult.ok("ok",StockUtils.parseHistoryPrice(fullCode, lines));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("获取k 线失败。");
    }
    @GetMapping("/getTradeDate")
    public HttpResult getTradeDate(Integer count, String toTime){
        try {
            List<TradeDate> rets = tradeDateService.getTradeDate(count, toTime);
            return HttpResult.ok("ok",rets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("获取交易日列表失败。。");
    }

    @GetMapping("/addTradeDate")
    public HttpResult addTradeDate(String dates){
        try {
            List<String> dList = StringUtils.toList(dates);
            for(String d: dList) {
                tradeDateService.addTradeDate(d.replaceAll("\"",""));
            }
            return HttpResult.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加交易日失败。。");
    }


    @PostMapping("/addStock")
    public HttpResult addSelectStock(@RequestBody String codes) {
        try {
            List<String> codeList = StringUtils.toList(codes.replaceAll("\"", ""));
            log.info("thirdParty add SelectStock codes: " + codes + "; size:" + codeList.size());
            stockService.addThirdPartSelStock(codeList);
            return HttpResult.ok("添加成功!");
        } catch (Exception e) {
            log.error("add stock exception: ", e);
            return HttpResult.error("添加失败!");
        }
    }

    @GetMapping("/getAllStockCodes")
    public HttpResult getAllStockCodes() {
        try {
            StringBuilder ret = new StringBuilder();
            List<Stocks> ss = stockService.getAllStocks();
            for (Stocks s: ss) {
                ret.append(s.getCode()).append(",");
            }
            return HttpResult.ok("查询成功!", ret.toString());
        } catch (Exception e) {
            log.error("select stock exception: ", e);
            return HttpResult.error("查询失败!");
        }
    }

    @PostMapping("/addNewStocks")
    public HttpResult addNewStocks(@RequestBody List<Stocks> stocks) {
        try {
            Map<String, Stocks> ret = new HashMap<>();
            List<Stocks> ss = stockService.getAllStocks();
            for (Stocks s: ss) {
                ret.put(s.getCode(), s);
            }
            for (Stocks s1: stocks) {
                if (ret.get(s1.getCode()) == null) {
                    stockService.insertStocks(s1);
                }
            }
            return HttpResult.ok("添加成功!", ret.toString());
        } catch (Exception e) {
            log.error("add stock exception: ", e);
            return HttpResult.error("添加失败!");
        }
    }
}
