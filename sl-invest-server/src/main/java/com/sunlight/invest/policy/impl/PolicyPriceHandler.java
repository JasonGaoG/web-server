package com.sunlight.invest.policy.impl;

import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.service.PolicyPriceService;
import com.sunlight.invest.vo.PolicyPriceVo;
import com.sunlight.invest.vo.StockInfoVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 股价新高或者新低的处理。
 */
@Component
public class PolicyPriceHandler implements PolicyHandler<StockInfoVo> {

    @Resource
    private PolicyPriceService policyPriceService;

    @Override
    public void handle(List<StockInfoVo> list) {
        List<PolicyPriceVo> olds = policyPriceService.getAllPolicyPrices();
        Map<String, PolicyPriceVo> map = new HashMap<>();
        olds.forEach(pp -> {
            map.put(pp.getCode(), pp);
        });
        for (StockInfoVo infoVo: list) {
            PolicyPriceVo old = map.get(infoVo.getCode());
            if (old != null) {
                String prices = old.getPrices()+"," + infoVo.getCurrentPrice();
                List<String> priceList = StringUtils.toList(prices);
                if (priceList.size() > 600) {
                    prices = prices.substring(prices.indexOf(","));
                }
                old.setPrices(prices);
                String date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
                boolean update = false;
                if (infoVo.getUpPrice() > 0) {
                    // 上涨
                    if (infoVo.getCurrentPrice() > old.getNewHigh20()) {
                        old.setNewHigh20(infoVo.getCurrentPrice());
                        old.setNewHigh20Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() > old.getNewHigh60()) {
                        old.setNewHigh60(infoVo.getCurrentPrice());
                        old.setNewHigh60Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() > old.getNewHigh90()) {
                        old.setNewHigh90(infoVo.getCurrentPrice());
                        old.setNewHigh90Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() > old.getNewHigh120()) {
                        old.setNewHigh120(infoVo.getCurrentPrice());
                        old.setNewHigh120Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() > old.getNewHighEst()) {
                        old.setNewHighEst(infoVo.getCurrentPrice());
                        old.setNewHighEstDate(date);
                        update = true;
                    }
                } else {
                    if (infoVo.getCurrentPrice() < old.getNewLow20()) {
                        old.setNewLow20(infoVo.getCurrentPrice());
                        old.setNewLow20Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() < old.getNewLow60()) {
                        old.setNewLow60(infoVo.getCurrentPrice());
                        old.setNewLow60Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() < old.getNewLow90()) {
                        old.setNewLow90(infoVo.getCurrentPrice());
                        old.setNewLow90Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() < old.getNewLow120()) {
                        old.setNewLow120(infoVo.getCurrentPrice());
                        old.setNewLow120Date(date);
                        update = true;
                    }
                    if (infoVo.getCurrentPrice() < old.getNewLowEst()) {
                        old.setNewLowEst(infoVo.getCurrentPrice());
                        old.setNewLowEstDate(date);
                        update = true;
                    }
                }
                if (update) {
                    policyPriceService.update(old);
                }
            } else {
                old = new PolicyPriceVo(infoVo);
                policyPriceService.add(old);
            }
        }
    }
}
