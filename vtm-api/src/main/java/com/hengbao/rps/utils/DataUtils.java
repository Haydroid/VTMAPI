package com.hengbao.rps.utils;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : DataUtils
 */

public class DataUtils {

    public static int isOdd(int num) {
        return num & 0x1;
    }

    public static int HexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static String Byte2Hex(Byte inByte) {
//        return String.format("%02x", inByte).toUpperCase();
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    public static String ByteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        byte[] var2 = inBytArr;
        int var3 = inBytArr.length;

/*
        for(int var4 = 0; var4 < var3; ++var4) {
            byte valueOf = var2[var4];
            strBuilder.append(Byte2Hex(valueOf));
            strBuilder.append(" ");
        }
*/

        for (byte valueOf : inBytArr) {
            strBuilder.append(Byte2Hex(Byte.valueOf(valueOf)));
            strBuilder.append(" ");
        }

        return strBuilder.toString();
    }

    public static String ByteArrToHex(byte[] inBytArr, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;

        for (int i = offset; i < j; i++) {
            strBuilder.append(Byte2Hex(inBytArr[i]));
        }

        return strBuilder.toString();
    }

    public static byte[] HexToByteArr(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (isOdd(hexlen) == 1) {
            hexlen++;
//            ++hexlen;
            result = new byte[hexlen / 2];
            inHex = "0" + inHex;
        } else {
            result = new byte[hexlen / 2];
        }

        int j = 0;

        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }

        return result;
    }

    public static String ByteToString(byte[] inBytArr, int offset, int byteCount) {
        byte[] srtbyte = new byte[byteCount];
        System.arraycopy(inBytArr, offset, srtbyte, 0, byteCount);
        String res = new String(srtbyte);
        System.out.println(res);
        return res;
    }
}
