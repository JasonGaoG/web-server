package com.sunlight.common.utils;

import java.security.MessageDigest;

/**
 * @author Administrator
 * @description md5 加密
 * @date 2018/11/2
 */
public class MD5Util {

	/*** 
     * MD5加密 生成32位md5码
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();
    }	

	/**
	 * @param args
	 * 2014-12-17
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 String str = new String("123456");
	      System.out.println("原始：" + str);
	      System.out.println("MD5后：" + md5Encode(str));
	}

}
