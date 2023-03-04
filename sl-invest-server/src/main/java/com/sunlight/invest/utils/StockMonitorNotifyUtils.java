package com.sunlight.invest.utils;

import com.sunlight.common.utils.HttpUtils;
import com.sunlight.common.utils.StringUtils;
import com.sunlight.invest.vo.WeChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class StockMonitorNotifyUtils {

    private static String notifyUrl;

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    @Value("${notify.wechat.url}")
    public void setNotifyUrl(String url) {
        StockMonitorNotifyUtils.notifyUrl = url;
    }

    public static void sendAlert(String code, Double price){
        try {
            String fullCode = StockUtils.getCodePrefix(code) + code;
            String url = "http://47.117.132.1:9527/code?code="+fullCode+"&price=" + price;
            String res = HttpUtils.doGet(url);
            log.info("sendAlert code: " + code + ", resp: " + res);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("send Alert exception, code: " + code);
        }
    }

    // 异步通知。。
    public static void sendNotify(String message, String notUrl) {
        if (StringUtils.isBlank(notUrl)) {
            notUrl = notifyUrl;
        }
        String finalNotUrl = notUrl;
        String res = HttpUtils.doPost(finalNotUrl, new WeChatMessage(message));
    }
}
