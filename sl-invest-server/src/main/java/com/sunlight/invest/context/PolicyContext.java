package com.sunlight.invest.context;

import com.sunlight.invest.policy.PolicyHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 策略管理上下文
 */
@Slf4j
public class PolicyContext {
    private PolicyContext(){}

    private static PolicyContext instance = new PolicyContext();

    public static PolicyContext getInstance() {
        if (instance == null) {
            instance = new PolicyContext();
        }
        return instance;
    }

    // 自选股早上十点前 大涨或者大跌7个点以上的通知列表和次数。触发通知后，通知三次不再通知。
    private static Map<String, Integer> notified = new HashMap<>();

    public void addNotified(String code, Integer count){
        notified.put(code, count);
    }

    public void clearNotified(){
        notified = new HashMap<>();
    }

    private final List<PolicyHandler> handlers = new ArrayList<>();

    public void addHandlers(PolicyHandler handler) {
        handlers.add(handler);
    }

    public List<PolicyHandler> getHandlers() {
        return handlers;
    }

    public Integer getNotified(String code) {
        return  notified.get(code);
    }

    // 每天早上9点26 订阅自选股
}
