package com.hengbao.rps.utils;

import java.io.UnsupportedEncodingException;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : StringUtil
 */

public class StringUtil {
    public StringUtil() {
    }

/*
    public static String getStr(String s) {
        char[] c = s.toCharArray();
        char temp = false;

        for(int i = 0; i < c.length - 1; i += 2) {
            char temp = c[i];
            c[i] = c[i + 1];
            c[i + 1] = temp;
        }

        return new String(c);
    }
*/

    public static String getStr(String s) {
        char[] c = s.toCharArray();

        char temp = '\000';
        for (int i = 0; i < c.length - 1; i += 2) {
            temp = c[i];
            c[i] = c[(i + 1)];
            c[(i + 1)] = temp;
        }
        return new String(c);
    }


    public static String HEX_2_ASC(String hex) {
        String asc = null;
        int len = hex.length();
        byte[] bs = new byte[len / 2];

        for (int i = 0; i < len / 2; i++) {
            bs[i] = Byte.parseByte(hex.substring(i * 2, i * 2 + 2), 16);
        }

        try {
            asc = new String(bs, "GBK");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        return asc;
    }

    public static String byte2HexString(byte[] b) {
        String a = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);//255
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            a = a + hex;
        }

        return a;
    }

    public static Boolean isBlank(String param) {
        Boolean res = true;
        if (param != null && !"".equals(param)) {
            res = false;
        }

        return res;
    }
}
