package com.sunlight.common.utils;

import com.alibaba.fastjson.JSON;
import com.sunlight.common.vo.DingMessageVO;
import com.sunlight.common.vo.TextVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HttpUtils {

    public static String doPost(String url, Object param) {
        HttpURLConnection connection;
        InputStream stream;
        BufferedReader reader;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream os = connection.getOutputStream();
            os.write(JSON.toJSONString(param).getBytes(StandardCharsets.UTF_8));
            connection.connect();
            if (connection.getResponseCode() == 200) {
                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                StringBuilder sbf = new StringBuilder();
                String temp = null;
                while ((temp = reader.readLine()) != null) {
                    sbf.append(temp);
                }
                System.out.println(sbf.toString());
                return sbf.toString();
            }
        } catch (Exception e) {
            log.error("post exception:", e);
        }
        return null;
    }

    public static String doGet(String url) {
        return doGet(url, "UTF-8");
    }

    public static String doGet(String reqUrl, String encode){
        HttpURLConnection connection;
        InputStream stream;
        BufferedReader reader;
        try {
            URL url = new URL(reqUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream, encode));
                StringBuilder sbf = new StringBuilder();
                String temp = null;
                while ((temp = reader.readLine()) != null) {
                    sbf.append(temp);
                }
                return sbf.toString();
            }
        } catch (MalformedURLException e) {
            log.error("new URL Exception:", e);
        } catch (IOException e) {
            log.error("openConnection exception:", e);
        }
        return null;
    }

    public static void sendDingMessage(String message) {
        try {
            Long timestamp = System.currentTimeMillis();
            String secret = "SEC977e77dbcb38599b405a1957bd41f33367b97777555ee3b54410ae2b18f4e197";

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            TextVO textVO = new TextVO();
            textVO.setContent(message);
            DingMessageVO dingMessageVO = new DingMessageVO();
            dingMessageVO.setMsgtype("text");
            dingMessageVO.setText(textVO);
            doPost("https://oapi.dingtalk.com/robot/send?access_token=6d033df5651953319757e7140702c136d53226052dcb11c40a48dd1867d54753&sign=" + sign + "&timestamp=" + timestamp,
                    dingMessageVO);
        } catch (Exception e) {
            log.error("ding message error", e);
        }

    }

    public static void main(String args[]) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC977e77dbcb38599b405a1957bd41f33367b97777555ee3b54410ae2b18f4e197";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        TextVO textVO = new TextVO();
        textVO.setContent("test message");
        DingMessageVO dingMessageVO = new DingMessageVO();
        dingMessageVO.setMsgtype("text");
        dingMessageVO.setText(textVO);
        doPost("https://oapi.dingtalk.com/robot/send?access_token=6d033df5651953319757e7140702c136d53226052dcb11c40a48dd1867d54753&sign=" + sign + "&timestamp=" + timestamp,
                dingMessageVO);
    }
}
