package com.hengbao.rps.utils;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 加解密工具类
 */

public class EncodeUtil {
    protected static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected static final char[] BINARY = new char[]{'0', '1'};

    public EncodeUtil() {
    }

    public static void main(String[] args) {
        binary("01001000");
    }

    public static byte[] binary(String binaryStr) {
        if (binaryStr.length() % 8 != 0) {
            throw new IllegalArgumentException("传入的参数长度必须是8的倍数");
        } else {
            StringBuffer accum = new StringBuffer();

            for (int i = 0; i < binaryStr.length(); i += 4) {
                String temp = binaryStr.substring(i, i + 4);
                int value = 0;

                for (int j = 0; j < 4; j++) {
                    if (temp.charAt(j) == '1') {
                        value = (int) ((double) value + Math.pow(2.0D, (double) (3 - j)));
                    }
                }

                accum.append(HEX[value]);
            }

            return bcd(accum.toString());
        }
    }

    public static String hex(byte[] bts, int offset, int length) {
        if (offset >= 0 && length >= 0 && bts.length >= offset + length - 1) {
            byte[] returnBt = new byte[length];
            System.arraycopy(returnBt, 0, bts, offset, length);
            return hex(returnBt);
        } else {
            throw new IllegalArgumentException("参数非法：offset:" + offset + ",length:" + length + ",字节长度：" + bts.length);
        }
    }

    public static String hex(byte[] bParam) {
        StringBuilder accum = new StringBuilder();

        for (byte bt : bParam) {
            accum.append(HEX[(bt >> 4 & 0xF)]);
            accum.append(HEX[(bt & 0xF)]);
        }

/*
        byte[] var2 = bParam;
        int var3 = bParam.length;
        for(int var4 = 0; var4 < var3; ++var4) {
            byte bt = var2[var4];
            accum.append(HEX[bt >> 4 & 15]);
            accum.append(HEX[bt & 15]);
        }
*/

        return accum.toString();
    }

    public static byte[] bcd(int code, int len) {
        return bcd(String.valueOf(code), len);
    }

    public static String binary(byte[] bts) {
        StringBuffer accum = new StringBuffer();

        for (byte bt : bts) {
            accum.append(binary(bt));
        }

/*
        byte[] var2 = bts;
        int var3 = bts.length;
        for(int var4 = 0; var4 < var3; ++var4) {
            byte bt = var2[var4];
            accum.append(binary(bt));
        }
*/

        return accum.toString();
    }

    private static String binary(byte bt) {
        int num = bt & 0xFF;
        char[] arrayOfChar = new char[8];
        int i = 8;
        for (int times = 0; times < 8; times++) {
            arrayOfChar[(--i)] = BINARY[(num & 0x1)];
            num >>>= 1;
        }
        return new String(arrayOfChar);
    }

/*
    private static String binary(byte bt) {
        int num = bt & 255;
        char[] arrayOfChar = new char[8];
        int i = 8;

        for(int times = 0; times < 8; ++times) {
            --i;
            arrayOfChar[i] = BINARY[num & 1];
            num >>>= 1;
        }

        return new String(arrayOfChar);
    }
*/

    public static byte[] bcd(String code) {
        int len = code.length() % 2 == 0 ? code.length() / 2 : code.length() / 2 + 1;
        return bcd(code, len);
    }

    public static byte[] bcd(String code, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("参数length不能小于0,length:" + length);
        } else if (length == 0) {
            return new byte[0];
        } else {
            byte[] bt = new byte[length];
            int point = 0;
            if (code.length() < 2 * length) {
                code = addBlankLeft(code, 2 * length - code.length(), "0");
            }

            while (point < code.length()) {
                bt[(point + 1) / 2] = (byte) (Character.digit(code.charAt(point), 16) << 4 | Character.digit(code.charAt(point + 1), 16));
                point += 2;
            }

            return bt;
        }
    }

    public static String addBlankLeft(String origStr, int length, String fill) {
        if (length <= 0) {
            return origStr;
        } else {
            StringBuffer accum = new StringBuffer();

            for (int i = 0; i < length; i++) {
                accum.append(fill);
            }

            accum.append(origStr);
            return accum.toString();
        }
    }

    public static String addBlankRight(String origStr, int length, String fill) {
        if (length <= 0) {
            return origStr;
        } else {
            StringBuffer accum = new StringBuffer(origStr);

            for (int i = 0; i < length; i++) {
                accum.append(fill);
            }

            return accum.toString();
        }
    }

    public static byte[] unbcd(byte[] src) {
        byte[] tag = new byte[src.length * 2];

        for (int i = 0; i < src.length; i++) {
/*
            tag[2 * i] = (byte)(src[i] >> 4 & 15);
            tag[2 * i + 1] = (byte)(src[i] & 15);
*/

            tag[(2 * i)] = ((byte) (src[i] >> 4 & 0xF));
            tag[(2 * i + 1)] = ((byte) (src[i] & 0xF));
        }

        return tag;
    }

    public static int bcd2Int(byte[] src) {
        int num = 0;

        for (int i = 0; i < src.length; i++) {
            num *= 10;
            num += src[i] >> 4;
            num *= 10;
            num += src[i] & 0xF;
        }

        return num;
    }
}
