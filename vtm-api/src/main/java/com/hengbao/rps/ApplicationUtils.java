package com.hengbao.rps;

import com.hengbao.rps.entity.VendorEntity;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : ApplicationUtils
 */

public class ApplicationUtils {
    public static String SM_PORT = "";
    public static long BAUDRATE = 0L;
    public static byte WORK_KEY_ID = -1;
    public static byte EncryptMode = 1;
    public static byte AlgorithmMode = 4;
    public static VendorEntity vendor;

    public ApplicationUtils() {
    }

    public static VendorEntity getVendor() {
        return vendor;
    }

    public static void setVendor(VendorEntity vendor) {
        vendor = vendor;
    }
}
