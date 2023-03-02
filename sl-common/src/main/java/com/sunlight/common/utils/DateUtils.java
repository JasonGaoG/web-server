/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 */

package com.sunlight.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 * @description 日期操作类
 * @date 2018/11/28
 */
public class DateUtils {

    /**
     * 获取完整的Date格式的当前时间
     *
     * @return date
     */
    public static Date getCurrentDateTime() {

        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 根据日期　获取　获取标准的8位日期字符串 　yyyy-MM-dd
     *
     * @param format "yyyy-MM-dd" "yyyy-MM-dd hh:mm:ss"
     * @return 标准的8位日期字符串
     */
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 系统时间戳字符串
     *
     * @return
     */
    public static String timeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取当前时间后几天后的日期
     */
    public static Date getDateDayAimToNow(int daynum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, daynum);
        return cal.getTime();
    }

    /**
     * 获取指定时间后几天后的日期
     */
    public static Date getFixDateDay(Date date, int daynum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, daynum);
        return cal.getTime();
    }

    /**
     * 字符串转换成日期
     *
     * @param str str
     * @return date
     */
    public static Date strToDate(String str, String formatStr) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date strToDate(String str) {
        return strToDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取时间，如2015-10-01 09:45:12 ---> 09:45:12
     *
     * @param date
     * @return
     */
    public static String splitDate(String date) {
        String[] tran = date.split(" ");
        return tran[1];
    }

    /**
     * 获取当年 第一天 日期
     *
     * @return 2015-12-29
     */
    public static Date getFirstDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int daynum = 1 - cal.get(Calendar.DAY_OF_YEAR);
        return getDateDayAimToNow(daynum);
    }

    /**
     * 获取月 第一天 日期
     *
     * @return 2015-12-29
     */
    public static Date getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int daynum = 1 - cal.get(Calendar.DAY_OF_MONTH);
        return getDateDayAimToNow(daynum);
    }

    /**
     * 获取本周 第一天 日期
     *
     * @return 2015-12-29
     */
    public static Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int daynum = 1 - cal.get(Calendar.DAY_OF_WEEK);
        return getDateDayAimToNow(daynum);
    }

    public static String getYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        if (date == null) {
            date = new Date();
        }
        return sdf.format(date);
    }

    /*
     * 两个日期相差的天数
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 比较日期大小
     *
     * @throws ParseException
     */
    public static boolean compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return true;
            } else if (dt1.getTime() > dt2.getTime()) {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("出错了:" + e);
        }
        return false;
    }

    public static void main(String[] args) throws ParseException {

        Date d = new Date(1556525292564L);
        System.out.println(getDateString(d));
    }

}
