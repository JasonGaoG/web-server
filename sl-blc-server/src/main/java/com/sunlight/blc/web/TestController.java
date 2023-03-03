package com.sunlight.blc.web;

import com.binance.connector.client.impl.SpotClientImpl;
import com.sunlight.blc.service.BinanceService;
import com.sunlight.blc.vo.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@RestController
@Slf4j
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private BinanceService binanceService;

    @GetMapping("/binanceTest2")
    public HttpResult binanceTest2(){
        System.out.println("start tests....");
        try {
            LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

            SpotClientImpl client = new SpotClientImpl("2R83Da3KYpIuoM7C4FaYS6D2F49MtejuPE1CbeFYULqyHnvgnsI793bEsFLZ5DT2",
                    "oMHxkCuPh4Tow3DuBgsqYl4ZlhDGBXA0sZrexNYSc34yg4Tzt4FUonUwykK2Ogma");
            String result = client.createTrade().account(parameters);
            log.info(result);
           parameters.put("symbol", "XVGBUSD");
        parameters.put("side","SELL");
        parameters.put("type","MARKET");
        parameters.put("quantity", 7000);
        String result2 = client.createTrade().newOrder(parameters);
        log.info("sell currency, result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.ok("查询成功!");
    }
}