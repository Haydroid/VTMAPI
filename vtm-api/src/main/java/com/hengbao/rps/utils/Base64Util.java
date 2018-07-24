package com.hengbao.rps.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : Base64Util
 */

public class Base64Util {

    public Base64Util() {
    }

    // 加密
    public static String getBase64Encode(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("UTF-8"), Base64.NO_WRAP), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getBase64Decode(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        getBase64Encode("haydroid");
    }

}
