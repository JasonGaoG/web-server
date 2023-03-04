package com.sunlight.invest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunlight.common.utils.HttpUtils;
import com.sunlight.invest.constant.Constant;
import com.sunlight.invest.vo.StockInfoVo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class StockUtils {

    private static final List<String> CODES_SH_A = Arrays.asList("600", "601", "603", "605", "688", "689");
    private static final List<String> CODES_SZ_A = Arrays.asList("000", "001", "002", "003", "004", "300", "301");

    public static String getCodePrefix(String code){
        if (CODES_SH_A.stream().anyMatch(code::startsWith)) {
            return "sh";
        }
        return "sz";
    }

//    private static String test = "v_sh601003=\"1~柳钢股份~601003~4.08~4.12~4.13~130907~36957~93951~4.08~5565~4.07~3591~4.06~3059~4.05~4277~4.04~685~4.09~2~4.10~753~4.11~3148~4.12~1771~4.13~3693~~20220627155904~-0.04~-0.97~4.14~4.08~4.08/130907/53664485~130907~5366~0.51~37.27~~4.14~4.08~1.46~104.56~104.56~0.90~4.53~3.71~1.48~7810~4.10~-5.11~7.18~~~0.91~5366.4485~0.0000~0~ ~GP-A~-19.11~-0.97~4.31~2.33~0.89~8.08~3.87~-1.31~0.39~-12.89~2562793200~2562793200~29.42~-44.14~2562793200~~\";v_sh601001=\"1~晋控煤业~601001~16.89~15.44~15.45~469117~293308~175810~16.89~759~16.88~880~16.87~167~16.86~46~16.85~397~16.90~793~16.91~21~16.92~27~16.93~11~16.94~9~~20220627155916~1.45~9.39~16.98~15.31~16.89/469117/771007187~469117~77101~2.80~5.59~~16.98~15.31~10.82~282.69~282.69~2.18~16.98~13.90~1.21~1388~16.44~7.85~6.07~~~1.61~77100.7187~0.0000~0~ ~GP-A~75.75~7.65~0.00~38.97~16.85~18.61~6.69~1.08~10.46~3.62~1673700000~1673700000~44.63~19.70~1673700000~~\";";
    /**
     * @param stockStr test
     * @return ret
     */
    public static List<StockInfoVo> paresTXStock(String stockStr) {
        List<StockInfoVo> ret = new ArrayList<>();
        String[] arrs = stockStr.split(";");
        for(String s: arrs) {
            try {
                String[] infos = s.split("~");
                String name = infos[1];
                String code = infos[2];
                StockInfoVo vo = new StockInfoVo();
                vo.setName(name);
                vo.setCode(code);
                vo.setCurrentPrice(Double.valueOf(infos[3]));
                vo.setYesterdayPrice(Double.valueOf(infos[4]));
                vo.setOpenPrice(Double.valueOf(infos[5]));
                vo.setTurnOver(Double.valueOf(infos[7]));
                vo.setUpPrice(Double.valueOf(infos[31]));
                vo.setUpPercent(Double.valueOf(infos[32]));
                vo.setHighestPrice(Double.valueOf(infos[33]));
                vo.setLowestPrice(Double.valueOf(infos[34]));
                vo.setExchangePercent(Double.valueOf(infos[38]));
                vo.setLimitUpPrice(Double.valueOf(infos[47]));
                vo.setLimitLowPrice(Double.valueOf(infos[48]));
                ret.add(vo);
            } catch (Exception e) {
                log.error("parse exception: ", e);
            }
        }
        return ret;
    }

    // 多个股票代码 逗号 拼接
    public static String getStockDetail(String code) {
        String url = "http://qt.gtimg.cn/q="; // 腾讯行情
        return HttpUtils.doGet(url + code, "gbk");
    }

    // 单个股票代码
    public static String getStockHistoryLines(String code, String start, String end, Integer count){
        if (count == null) {
            count = 320;
        }
//        https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=sh600519,day,2020-3-1,2021-3-1,500,qfq
        String url = "https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=";
        return HttpUtils.doGet(url + code + ",day,"+start+","+end+","+count +",qfq");
    }

    public static List<Map<String, String>> parseHistoryPrice(String code, String his){
        System.out.println(his);
        List<Map<String, String>> list = new ArrayList<>();
        JSONObject object = JSON.parseObject(his);
        JSONObject data = object.getJSONObject("data").getJSONObject(code);
        JSONArray arr = data.getJSONArray("qfqday");
        if (arr == null) {
            arr = data.getJSONArray("day");
        }
        if (arr == null) {
            log.info("历史价格获取为null。。。");
            return list;
        }
        for(int i = 0; i < arr.size(); i++) {
            Map<String, String> ret = new HashMap<>();
            Object[] line = arr.getJSONArray(i).toArray();
            String closePrice = line[2].toString();
            ret.put("trade-date", line[0].toString());
            ret.put("open-price", line[1].toString());
            ret.put("close-price", closePrice.substring(0, closePrice.length() -1));
            ret.put("high-price", line[3].toString());
            ret.put("low-price", line[4].toString());
            list.add(ret);
        }
        return list;
    }

    public static void main(String[] args){
//        String ret = getStockDetail("sh600009,sh603660");
//        List<StockInfoVo> rets = paresTXStock(ret);
//        String his = getStockHistoryLines("sh603660", "2020-01-01", "2021-02-02", 3);
//        log.info(his);
//        System.out.println(JSON.toJSONString(parseHistoryPrice("sh603660", his)));
//        StringBuilder builder = new StringBuilder();
//        builder.append("sh600366").append(",").append("sh600009").append(",");
//        System.out.println(builder.substring(0, builder.length()-1));


        StringBuilder sb = new StringBuilder();
        sb.append("sh").append(Constant.SH_A).append(",").append("sh").append(Constant.SH_K).append(",")
                .append("sz").append(Constant.SZ_A).append(",").append("sz").append(Constant.SZ_C);
        // 上证A
        String str = StockUtils.getStockDetail(sb.toString());
        List<StockInfoVo> vos = StockUtils.paresTXStock(str);
        for (StockInfoVo vo: vos) {
            log.info(vo.getName());
            log.info(vo.getCode());
            log.info(vo.getCurrentPrice()+"");
            log.info(vo.getUpPercent()+"");
        }
    }
}