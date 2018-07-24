package com.bonc.vtm.encryption;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : TDESUtil
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TDESUtil {

    private static final String Algorithm = "DESede"; //定義 加密算法,可用 DES,DESede,Blowfish

    private static final String AlgorithmP = "DESede/ECB/NoPadding";

    public static String Decrypt3DES(String value, String key) throws Exception {

//        byte[] b = decryptMode(GetKeyBytes(key), Base64.decode(value));
        byte[] b = decryptMode(GetKeyBytes(key), HexUtil.decodeHex(value.toCharArray()));

        return new String(b);

    }


    public static String Encrypt3DES(String value, String key) throws Exception {
//        String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));
        String str = HexUtil.encodeHexStr(encryptMode(GetKeyBytes(key), value.getBytes()));
        return str;
    }


    //計算24位長的密碼byte值,首先對原始密鑰做MD5算hash值，再用前8位數據對應補全後8位
    public static byte[] GetKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        byte[] bkey = strKey.getBytes();
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }
        for (int i = start; i < 24; i++) {//為了與.net16位key兼容
            bkey24[i] = bkey[i - start];
        }
        return bkey24;
    }


    //keybyte為加密密鑰，長度為24字節

    //src為被加密的數據緩沖區（源）

    public static byte[] encryptMode(byte[] keybyte, byte[] src) {

        try {
            //生成密鑰

            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密

            Cipher c1 = Cipher.getInstance(AlgorithmP);

            c1.init(Cipher.ENCRYPT_MODE, deskey);

            return c1.doFinal(src);

        } catch (java.security.NoSuchAlgorithmException e1) {

            e1.printStackTrace();

        } catch (javax.crypto.NoSuchPaddingException e2) {

            e2.printStackTrace();

        } catch (java.lang.Exception e3) {

            e3.printStackTrace();

        }

        return null;

    }


    //keybyte為加密密鑰，長度為24字節

    //src為加密後的緩沖區

    public static byte[] decryptMode(byte[] keybyte, byte[] src) {

        try { //生成密鑰

            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密

            Cipher c1 = Cipher.getInstance(AlgorithmP);

            c1.init(Cipher.DECRYPT_MODE, deskey);

            return c1.doFinal(src);

        } catch (java.security.NoSuchAlgorithmException e1) {

            e1.printStackTrace();

        } catch (javax.crypto.NoSuchPaddingException e2) {

            e2.printStackTrace();

        } catch (java.lang.Exception e3) {

            e3.printStackTrace();

        }

        return null;

    }


    //轉換成base64編碼

    public static String byte2Base64(byte[] b) {

        return Base64.encode(b.toString());

    }

    //轉換成十六進制字符串

    public static String byte2hex(byte[] b) {

        String hs = "";

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1)

                hs = hs + "0" + stmp;

            else

                hs = hs + stmp;

        }

        return hs.toUpperCase();

    }


    /**
     * 去掉java加密後會在後面自動填充的8位
     *
     * @param src
     * @return
     */

    public static byte[] withoutAutofill(byte[] src) {

        byte[] newbyte = new byte[src.length - 8];

        for (int i = 0; i < newbyte.length; i++) {

            newbyte[i] = src[i];

        }

        return newbyte;

    }


    public static void main(String[] args) throws Exception {
        String x = Encrypt3DES("0379845530160D95", "11111111111111111111111111111111");
        System.out.println(x);
        String y = Decrypt3DES(x, "11111111111111111111111111111111");
        System.out.println(y);
    }


}
