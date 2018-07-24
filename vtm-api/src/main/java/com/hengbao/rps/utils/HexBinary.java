package com.hengbao.rps.utils;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 十六进制和二进制转换
 */

public class HexBinary {
    public HexBinary() {
    }

    public static byte[] getClone(byte[] pHexBinary) {
        byte[] result = new byte[pHexBinary.length];
        System.arraycopy(pHexBinary, 0, result, 0, pHexBinary.length);
        return result;
    }

    public static byte[] decode(String pValue) {
        if (pValue.length() % 2 != 0) {
            throw new IllegalArgumentException("A HexBinary string must have even length.");
        } else {
            byte[] result = new byte[pValue.length() / 2];
            int j = 0;

            byte b;
            for (int i = 0; i < pValue.length(); result[j++] = b) {
                char c = pValue.charAt(i++);
                char d = pValue.charAt(i++);
                if (c >= '0' && c <= '9') {
                    b = (byte) (c - '0' << 4);//48
                } else if (c >= 'A' && c <= 'F') {
                    b = (byte) (c - 'A' + 10 << 4);//65
                } else {
                    if (c < 'a' || c > 'f') {
                        throw new IllegalArgumentException("Invalid hex digit: " + c);
                    }

                    b = (byte) (c - 97 + 10 << 4);
                }

                if (d >= '0' && d <= '9') {
                    b += (byte) (d - '0');//48
                } else if (d >= 'A' && d <= 'F') {
                    b += (byte) (d - 'A' + 10);//65
                } else {
                    if (d < 'a' || d > 'f') {
                        throw new IllegalArgumentException("Invalid hex digit: " + d);
                    }

                    b += (byte) (d - 'a' + 10);//97
                }
            }

            return result;
        }
    }

    public static String encode(byte[] pHexBinary) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < pHexBinary.length; i++) {
            byte b = pHexBinary[i];
//            byte c = (byte)((b & 240) >> 4);
            byte c = (byte) ((b & 0xF0) >> 4);
            if (c <= 9) {
                result.append((char) (48 + c));
            } else {
                result.append((char) (65 + c - 10));
            }

//            c = (byte)(b & 15);
            c = (byte) (b & 0xF);
            if (c <= 9) {
                result.append((char) (48 + c));
            } else {
                result.append((char) (65 + c - 10));
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String a = "1134325353njjj";
        System.out.println(new String(encode(a.getBytes())));
        String b = new String(encode(a.getBytes()));
        System.out.println(new String(decode(b)));
    }
}
