package com.qiugonglue.utils;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Util {

    /**
     * md5加密
     * 
     * @param str 需要md5加密的信息
     * @return 加密后的md5字符串
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        	try {
				throw new Exception("NoSuchAlgorithmException caught!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        return byte2hex(byteArray);
    }

    /**
     * 二行制转字符串
     * 
     * @param b
     * @return
     */
    @SuppressLint("DefaultLocale")
	public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toLowerCase();
    }

}
