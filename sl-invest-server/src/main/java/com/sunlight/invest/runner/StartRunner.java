package com.sunlight.invest.runner;

import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.service.TradeDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author Administrator
 * @package com.kedacom.blockchain.runner
 * @description 启动执行方法
 * @date 2019/4/1
 */
@Slf4j
@Order(1)
@Component
public class StartRunner implements CommandLineRunner {

    @Resource
    private TradeDateService tradeDateService;

    @Override
    public void run(String... args) {
        initTradeDate();
    }

    /**
     * @author Administrator
     * @description 初始化redis 缓存数据
     */
    private void initTradeDate() {
        Constant.IS_TRADE_DATE = tradeDateService.isTradeDate(null);
        log.info("------ isTradeDate -----: " + Constant.IS_TRADE_DATE);
    }
}
