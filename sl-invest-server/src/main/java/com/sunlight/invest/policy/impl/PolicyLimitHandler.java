package com.sunlight.invest.policy.impl;

import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.constant.PolicyLimitTypeEnum;
import com.sunlight.invest.model.PolicyLimit;
import com.sunlight.invest.model.Stocks;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.service.PolicyLimitService;
import com.sunlight.invest.service.StockService;
import com.sunlight.invest.vo.PolicyLimitVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 涨停，跌停，大涨，大跌 统计
 */
@Slf4j
@Component
public class PolicyLimitHandler implements PolicyHandler<StockInfoVo> {

    @Resource
    private PolicyLimitService policyLimitService;
    @Resource
    private StockService stockService;

    @Override
    public void handle(List<StockInfoVo> list) {
        processStatisStocksDayInfo(list);
    }

    private void processStatisStocksDayInfo(List<StockInfoVo> stockInfos){
        Map<String, String> plateMap = new HashMap<>();
        List<Stocks> ss = stockService.getAllStocks();
        ss.forEach(s -> {
            plateMap.put(s.getCode(), StringUtils.isBlank(s.getPlates()) ? "unknow": s.getPlates());
        });
        List<PolicyLimitVo> pls = policyLimitService.getPolicyLimitByDate(policyLimitService.getLastLimitDate());
        Map<String, PolicyLimitVo> tempMap = new HashMap<>();
        pls.forEach(p -> {
            tempMap.put(p.getCode(), p);
        });
        List<PolicyLimit> toAdd = new ArrayList<>();
        for(StockInfoVo vo : stockInfos) {
            PolicyLimit pl = new PolicyLimit();
            pl.setCode(vo.getCode());
            pl.setName(vo.getName());
            pl.setPlates(plateMap.get(vo.getCode()));
            pl.setLimitDate(DateUtils.getDateString(new Date(), "yyyy-MM-dd"));
            pl.setDelstatus(DelStatusEnum.UnDelete.getValue());
            pl.setCurrentPrice(vo.getCurrentPrice());
            pl.setUpPercent(vo.getUpPercent());
            pl.setHighestPrice(vo.getHighestPrice());
            pl.setLowestPrice(vo.getLowestPrice());
            if (vo.getLimitUpPrice().equals(vo.getCurrentPrice()) ||
                    vo.getLimitLowPrice().equals(vo.getCurrentPrice())) {
                Integer limitType = vo.getLimitLowPrice().equals(vo.getCurrentPrice()) ?
                        PolicyLimitTypeEnum.LIMIT_DOWN.getValue() : PolicyLimitTypeEnum.LIMIT_UP.getValue();
                PolicyLimitVo oldVo = tempMap.get(vo.getCode());
                boolean continueLimit = oldVo != null && oldVo.getLimitType().equals(limitType);
                pl.setDays(continueLimit ? oldVo.getDays() + 1 : 1);
                pl.setLimitType(limitType);
                toAdd.add(pl);
            } else {
                if (vo.getUpPercent() > 5 || vo.getUpPercent() < -5) {
                    Integer limitType = vo.getUpPercent() > 5 ?
                            PolicyLimitTypeEnum.LIMIT_5_UP.getValue() : PolicyLimitTypeEnum.LIMIT_5_DOWN.getValue();
                    PolicyLimitVo oldVo = tempMap.get(vo.getCode());
                    boolean continueLimit = oldVo != null && oldVo.getLimitType().equals(limitType);
                    pl.setDays(continueLimit ? oldVo.getDays() + 1 : 1);
                    pl.setLimitType(limitType);
                    toAdd.add(pl);
                }
            }
        }
        if (toAdd.size() > 0) {
            policyLimitService.insertBatch(toAdd);
        }
    }


}
