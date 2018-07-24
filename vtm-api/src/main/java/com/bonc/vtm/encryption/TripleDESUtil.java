package com.bonc.vtm.encryption;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 3DES-ECB 加解密
 */

import com.hengbao.rps.utils.DataUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TripleDESUtil {
    // 编码方式
    private static final String ENCODING = "UTF-8";
    // 产生密钥的算法名称
    public static final String KEY_ALGORITHM = "DESede";// 3DES算法：DESede  // DES算法：DES
    // 加解密算法 格式：算法名称/加密模式/填充模式 注意：ECB不使用IV参数
    public static final String CIPHER_ALGORITHM = "DESede/CBC/NoPadding";//CBC //ECB

    //////////////////////////////////////////////////////////////////////////

    /**
     * JAVA DES 加密
     */
    public static byte[] encryptDes(byte[] key, byte[] src) {
        try {
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 获取数据并执行加密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param key
     * @param src
     * @return
     */
    public static byte[] decryptDes(byte[] key, byte[] src) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
            // 获取数据并执行解密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * 3DES(双倍长) ECB 加密
     * 加密的原理是：
     * 1、使用密钥的前8个字节对数据的8个字节进行des加密得到temp1。
     * 2、使用密钥的后8个字节对temp1进行des解密得到temp2。
     * 3、使用密钥的前8个字节对temp2做des加密得到最后的密文。
     * 代码中间有几处转换，转换原理为“0x11“转换成字符串“11”，和将字符串“11”转换成字节“0x11”。
     */

    public static String encryptECB3Des(String key, String src) {
        System.out.println("encryptECB3Des->" + "key:" + key);
        System.out.println("encryptECB3Des->" + "src:" + src);
        int len = key.length();
        if (key == null || src == null) {
            return null;
        }
        if (src.length() % 16 != 0) {
            return null;
        }
        if (len == 32) {
            String outData = "";
            String str = "";
            for (int i = 0; i < src.length() / 16; i++) {
                str = src.substring(i * 16, (i + 1) * 16);
                outData += encECB3Des(key, str);
            }
            return outData;
        }
        return null;
    }

    public static String encECB3Des(String key, String src) {
        byte[] temp = null;
        byte[] temp1 = null;
        temp1 = encryptDes(DataUtils.HexToByteArr(key.substring(0, 16)), DataUtils.HexToByteArr(src));
        temp = decryptDes(DataUtils.HexToByteArr(key.substring(16, 32)), temp1);
        temp1 = encryptDes(DataUtils.HexToByteArr(key.substring(0, 16)), temp);
        return DataUtils.ByteArrToHex(temp1);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * 3DES CBC加密
     *
     * @param key        密钥
     * @param initVector IV
     * @param data       明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] initVector, byte[] data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        for (int k = 0; k < bOut.length; k++) {
            System.out.print(bOut[k] + " ");
        }
        System.out.println("");
        return bOut;
    }

    /**
     * 3DES(双倍长) CBC 加密 IV = "0000000000000000";
     *
     * @param IV
     * @param key
     * @param src
     * @return
     */
    public static String encryptCBC3Des(String IV, String key, String src) {
        String outData = "";
        String enc = IV;
        byte[] strBs = null;
        byte[] encBs = null;
        byte[] b2 = new byte[8];
        for (int i = 0; i < src.length() / 16; i++) {
            strBs = DataUtils.HexToByteArr(src.substring(i * 16, (i + 1) * 16));
            encBs = DataUtils.HexToByteArr(enc);
            for (int j = 0; j < b2.length; j++) {
                b2[j] = (byte) (encBs[j] ^ strBs[j]);
            }
            enc = encECB3Des(key, DataUtils.ByteArrToHex(b2));
            outData += enc;
        }
        return outData;
    }

    /**
     * 生成密钥key对象
     *
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * 3DES(双倍长) ECB 解密
     *
     * @param key
     * @param src
     * @return
     */
    public static String decryptECB3Des(String key, String src) {
        if (key == null || src == null) {
            return null;
        }
        if (src.length() % 16 != 0) {
            return null;
        }
        if (key.length() == 32) {
            String outData = "";
            String str = "";
            for (int i = 0; i < src.length() / 16; i++) {
                str = src.substring(i * 16, (i + 1) * 16);
                outData += decECB3Des(key, str);
            }
            return outData;
        }
        return null;
    }

    public static String decECB3Des(String key, String src) {
        byte[] temp2 = decryptDes(DataUtils.HexToByteArr(key.substring(0, 16)), DataUtils.HexToByteArr(src));
        byte[] temp1 = encryptDes(DataUtils.HexToByteArr(key.substring(16, 32)), temp2);
        byte[] dest = decryptDes(DataUtils.HexToByteArr(key.substring(0, 16)), temp1);
        return DataUtils.ByteArrToHex(dest);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * 3DES CBC解密
     *
     * @param key        密钥
     * @param initVector IV
     * @param data       Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] initVector, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(initVector);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * 3DES(双倍长) CBC 解密 IV = "0000000000000000";
     *
     * @param IV
     * @param key
     * @param src
     * @return
     */
    public static String decryptCBC3Des(String IV, String key, String src) {
        String outData = "";
        String enc = IV;
        String str = "";
        byte[] encBs = null;
        byte[] decBs = null;
        byte[] b2 = new byte[8];
        for (int i = 0; i < src.length() / 16; i++) {
            str = src.substring(i * 16, (i + 1) * 16);
            decBs = DataUtils.HexToByteArr(decECB3Des(key, str));
            encBs = DataUtils.HexToByteArr(enc);
            for (int j = 0; j < b2.length; j++) {
                b2[j] = (byte) (encBs[j] ^ decBs[j]);
            }
            enc = str;
            outData += DataUtils.ByteArrToHex(b2);
        }
        return outData;
    }

    //////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws Exception {
        String inputPlainText = "12345678";
        byte[] key = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7".getBytes();//Plaintext //Hex //密钥
        byte[] initVector = {1, 2, 3, 4, 5, 6, 7, 8};//iv偏移量 //0102030405060708
        byte[] data = inputPlainText.getBytes(ENCODING);
        System.out.println("3DES-CBC 加密前：" + inputPlainText + "   长度：" + data.length);
//3DES-CBC 加密
        byte[] encryption = des3EncodeCBC(key, initVector, data);
        System.out.print("3DES-CBC 加密后：");
        System.out.println(Base64.encode(encryption));
//3DES-CBC 解密
        byte[] deciphering = des3DecodeCBC(key, initVector, encryption);
        System.out.println("3DES-CBC 解密后：" + new String(deciphering, ENCODING));

        String deciphering_ecb = encryptECB3Des("12345678123456781234567812345678", "1234567812345678");
        System.out.println("3DES-ECB 加密后：" + deciphering_ecb);
        System.out.println("3DES-ECB 解密后：" + decryptECB3Des("12345678123456781234567812345678", deciphering_ecb));

    }

}