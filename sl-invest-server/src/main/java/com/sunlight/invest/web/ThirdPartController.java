package com.sunlight.invest.web;

import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.model.TradeDate;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.service.TradeDateService;
import com.sunlight.invest.utils.StockUtils;
import com.sunlight.invest.vo.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
            return new HttpResult(StockUtils.parseHistoryPrice(fullCode, lines));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("获取k 线失败。");
    }
    @GetMapping("/getTradeDate")
    public HttpResult getTradeDate(Integer count, String toTime){
        try {
            List<TradeDate> rets = tradeDateService.getTradeDate(count, toTime);
            return new HttpResult(rets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("获取交易日列表失败。。");
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
}
