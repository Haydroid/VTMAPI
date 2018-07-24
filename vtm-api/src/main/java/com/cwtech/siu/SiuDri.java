package com.cwtech.siu;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : iMode : 0 关闭  1 打开 。 iLightMode : 0 常亮、 1 闪烁  2 快闪  3 慢闪
 */

public class SiuDri {
    static {
        System.loadLibrary("CWSiu-lib");
    }

    public native int SIU_OpenDevice(String nPortId, long nBaudRate);

    public native int SIU_CloseDevice();

    public native int SIU_GetVersion(byte[] pVersion, int[] iDataLen);

    public native int SIU_SetBarCodeLight(int iMode, int iLightMode);

    public native int SIU_SetReadCardLight(int iMode, int iLightMode);

    public native int SIU_SetReceiptPrinterLight(int iMode, int iLightMode);

    public native int SIU_SetIDCardLight(int iMode, int iLightMode);

    public native int SIU_SetFingerLight(int iMode, int iLightMode);

    public native int SIU_SetRFReadCardLight(int iMode, int iLightMode);

//    public native int SIU_SetIsssCardLight(int iMode, int iLightMode);//无此函数

    public native int SIU_SetSimIssCardLight(int iMode, int iLightMode);

    public native int SIU_CheckClose();//返回值  : 1 有人  0没人  -1 报错

}
