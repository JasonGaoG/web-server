package com.sunlight.invest.policy.impl;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.service.RedisService;
import com.sunlight.invest.utils.StockMonitorNotifyUtils;
import com.sunlight.invest.vo.MonitorPriceVo;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class PolicyPriceMonitorHandler implements PolicyHandler<StockInfoVo> {

    @Resource
    private RedisService redisService;

    @Override
    public void handle(List<StockInfoVo> list) {
        for(StockInfoVo vo : list) {
            monitorPriceNotify(vo);
        }
    }

    private void monitorPriceNotify(StockInfoVo infoVo){
        Map<Object, Object> monitors = redisService.getObjectMap(Constant.REDIS_STOCK_MONITOR+infoVo.getCode());
        Set<Object> companyIds = monitors.keySet();
        Map<String, String> ret = new HashMap<>();
        for(Object id : companyIds) {
            Integer companyId = Integer.parseInt(id.toString());
            String monitorStr = monitors.get(id).toString();
            MonitorPriceVo vo = JSON.parseObject(monitorStr, MonitorPriceVo.class);
            notifyPriceToComp(infoVo, vo, companyId);
            ret.put(id.toString(), JSON.toJSONString(vo));
        }
        redisService.setMap(Constant.REDIS_STOCK_MONITOR + infoVo.getCode(), ret);
    }

    private void notifyPriceToComp (StockInfoVo infoVo, MonitorPriceVo vo, Integer companyId){
        if (infoVo == null || vo == null || companyId == null) {
            return;
        }
        String url = redisService.getString(Constant.REDIS_COMPANY_NOTIFY_URL + companyId);
        if (StringUtils.isBlank(url)) {

        }
        if (vo.getMonitorUpPercent() != null && vo.getMonitorUpPercent() != 0) {
            String tips = "; 涨幅：";
            boolean notify = false;
            if (vo.getMonitorUpPercent() > 0 && infoVo.getUpPercent() > vo.getMonitorUpPercent()) {
                notify = true;
            } else if (vo.getMonitorUpPercent() < 0 && infoVo.getUpPrice() < vo.getMonitorUpPercent()) {
                tips = "; 跌幅：";
                notify = true;
            }
            if (notify) {
                String msg = "监听的股票： " + infoVo.getName() + tips + infoVo.getUpPrice()
                        + ", 到达监听涨跌比例：" + vo.getMonitorUpPercent();
                StockMonitorNotifyUtils.sendNotify(msg, url);
                vo.setNotifyCount(vo.getNotifyCount() + 1);
                if (vo.getNotifyCount() >=3) {
                    vo.setNotifyCount(0);
                    vo.setMonitorUpPercent(null);
                }
            }
        }
        if (vo.getMonitorHighPrice() != null && vo.getMonitorHighPrice() != 0) {
            if (infoVo.getCurrentPrice() >= vo.getMonitorHighPrice()) {
                String msg = "监听的股票： " + infoVo.getName() + " 当前价格：" + infoVo.getCurrentPrice()
                        + ", 涨至监听价格：" + vo.getMonitorHighPrice();
                StockMonitorNotifyUtils.sendNotify(msg, url);
                vo.setNotifyCount(vo.getNotifyCount() + 1);
                if (vo.getNotifyCount() >=3) {
                    vo.setNotifyCount(0);
                    vo.setMonitorHighPrice(null);
                }
            }
        }
        if (vo.getMonitorLowPrice() != null && vo.getMonitorLowPrice() != 0) {
            if (infoVo.getCurrentPrice() <= vo.getMonitorLowPrice()) {
                String msg = "监听的股票： " + infoVo.getName() + " 当前价格：" + infoVo.getCurrentPrice()
                        + ", 跌至监听价格：" + vo.getMonitorLowPrice();
                vo.setNotifyCount(vo.getNotifyCount() + 1);
                StockMonitorNotifyUtils.sendNotify(msg, url);
                if (vo.getNotifyCount() >=3) {
                    vo.setNotifyCount(0);
                    vo.setMonitorLowPrice(null);
                }
            }
        }
    }

}
