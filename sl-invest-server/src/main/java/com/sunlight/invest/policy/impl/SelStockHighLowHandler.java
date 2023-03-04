package com.sunlight.invest.policy.impl;

import com.sunlight.common.constant.BooleanEnum;
import com.sunlight.common.constant.DelStatusEnum;
import com.sunlight.common.utils.DateUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.context.PolicyContext;
import com.sunlight.invest.dao.PolicySelHighLowNotifyMapper;
import com.sunlight.invest.model.PolicySelHighLowNotify;
import com.sunlight.invest.policy.PolicyHandler;
import com.sunlight.invest.service.PolicyService;
import com.sunlight.invest.utils.StockMonitorNotifyUtils;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自选股 大涨和大跌提醒
 */
@Slf4j
@Component
public class SelStockHighLowHandler implements PolicyHandler<StockInfoVo> {

    @Resource
    private PolicyService policyService;

    @Resource
    private PolicySelHighLowNotifyMapper policySelHighLowNotifyMapper;

    private Integer hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private Integer mins = Calendar.getInstance().get(Calendar.MINUTE);
    private boolean monitorTime = (hours*100 + mins >= 930) && (hours * 100 + mins < 1130);
    @Override
    public void handle(List<StockInfoVo> list) {
        log.info("SelStockHighLowHandler: " + list.size());
        if (list.size() > 0 && monitorTime) {
            list.forEach(infoVo -> {
                if (infoVo.getUpPercent() > 6.8 || infoVo.getUpPercent() < -6.8) {
                    Integer count = PolicyContext.getInstance().getNotified(infoVo.getCode());
                    if (count == null || count < 3) {
                        count = count == null ? 1 : count + 1;
                        PolicyContext.getInstance().addNotified(infoVo.getCode(), count);
                        String msg = "自选股票： " + infoVo.getName() + "，股票代码： "+infoVo.getCode()+ ", 当前价格： " + infoVo.getCurrentPrice() + ", 价格涨跌：" + infoVo.getUpPrice()
                                + ", 到达涨跌7个点：" + infoVo.getUpPercent() + "%, 监控时间：" + DateUtils.getDateString(new Date());
                        StockMonitorNotifyUtils.sendNotify(msg, null);
                        if (infoVo.getUpPercent() >= 6.8) {
                            StockMonitorNotifyUtils.sendAlert(infoVo.getCode(), infoVo.getCurrentPrice());
                        }
                        if (count == 3) {
                            // redis 记录通知股票信息 保存最近一个月的记录。
                            String date = DateUtils.getDateString(new Date(), "yyyy-MM-dd");
                            PolicySelHighLowNotify pn = new PolicySelHighLowNotify();
                            pn.setCode(infoVo.getCode());
                            pn.setNotifyType(infoVo.getUpPercent() > 6.8 ? Constant.NOTIFY_HIGH_UP: Constant.NOTIFY_LOW_DOWN);
                            pn.setNotifyDate(date);
                            List<PolicySelHighLowNotify> olds = policySelHighLowNotifyMapper.selectMany(pn);
                            if (olds != null && olds.size() > 0) {
                                return;
                            }
                            pn.setDelstatus(DelStatusEnum.UnDelete.getValue());
                            pn.setName(infoVo.getName());
                            pn.setPrices(infoVo.getCurrentPrice()+"");
                            pn.setDays(1);
                            pn.setHasBuy(0);
                            pn.setHasSell(0);
                            pn.setBadProfit(0D);
                            pn.setBestProfit(0D);
                            pn.setNotifyDate(date);
                            pn.setHighEstDate(date);
                            pn.setLowEstDate(date);
                            pn.setHighEstPrice(infoVo.getHighestPrice());
                            pn.setLowEstPrice(infoVo.getLowestPrice());
                            pn.setNotifyPrice(infoVo.getCurrentPrice());
                            try {
                                policyService.insertSelHighLowNotify(pn);
                            } catch (Exception e) {
                                e.printStackTrace();
                                StockMonitorNotifyUtils.sendNotify("保存自选股高价低价失败啦啦啦拉。。..:" + infoVo.getCode(), null);
                            }
                        }
                    }
                }
            });
        }
    }
}
