package com.sunlight.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 *
 */
public class StringUtils {


    /**
     * 生成32位唯一的uuid
     *
     * @return uuid
     */
    public static String generateUuid() {

        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18)
                + uuid.substring(19, 23) + uuid.substring(24, 36);
    }
    /**
     * 特殊字符校验
     * @param str 要校验的字符串
     * @return true 包含特殊字符
     *
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ `~!@#$%^&*+=|{}':;,\\[\\].<>/?！￥…【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean isSpecialChar(String str,String regEx) {
        if(isBlank(regEx)){
            return isSpecialChar(str);
        }
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * @param str 字符串
     * @return 判断字符串是否为数字
     */
    public static boolean isNumeric(String str) {
        String regex = "[0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否不为空 (用一句话描述方法的主要功能)
     *
     * @param str test
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return boolean
     */
    public static boolean isBlank(String str) {

        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean judgeLength(String str, int limit) {

        if (isBlank(str)) {
            return false;
        }

        if (str.length() > limit) {
            return false;
        }

        return true;
    }

    public static String getCurTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 数组 转化成 逗号隔开的字符串 aaa,bbb,ccc
     *
     * @param arrstring aa
     * @return String
     */
    public static String arrToDotstring(String[] arrstring) {
        String dotString = "";
        for (int i = 0; i < arrstring.length; i++) {
            dotString += arrstring[i];
            if ((i + 1) < arrstring.length) {
                dotString += ",";
            }
        }
        return dotString;
    }

    /**
     * "a,b,c,d" -> 数组
     *
     * @param dotstring
     * @return
     */
    public static String[] dotStringToArr(String dotstring) {
        return dotstring.split(",");
    }

    /**
     * @return
     */
    public static List<String> toList(String str) {
        return toList(str, ",");
    }

    /**
     * @return
     */
    public static String listToStr(List<String> str) {
        if (null == str || str.size() <= 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (String s : str) {
            builder.append(s + ",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /**
     * @return
     */
    public static List<String> toList(String str, String token) {

        String[] arr = str.split(token);
        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            if (isNotBlank(arr[i])) {
                strList.add(arr[i]);
            }
        }
        return strList;
    }

    public static List<Integer> toIntegerIds(String str) {
        List<Integer> result = new ArrayList<Integer>();
        List<String> idlist = toList(str, ",");
        if (idlist.size() > 0) {
            for (String id : idlist) {
                try {
                    result.add(Integer.valueOf(id));

                } catch (NumberFormatException e) {
                    System.out.println("toIntegerIds:" + id);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "0199";
        System.out.println(isNumeric(s));
    }
}
