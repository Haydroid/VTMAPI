package com.bonc.vtm.hardware.lamp;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : LampInterface
 */

public interface LampInterface {

    /**
     * 条码/二维码指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return iRet 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int barCodeLight(int iMode);

    /**
     * 读卡器指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return iRet 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int readCardLight(int iMode);

    /**
     * 打印指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return iRet 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int receiptPrinterLight(int iMode);

    /**
     * 身份证指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int iDCardLight(int iMode);

    /**
     * 指纹指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int fingerLight(int iMode);

    /**
     * 非接指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int rFReadCardLight(int iMode);

    /**
     * 发卡器指示灯
     *
     * @param iMode 0 关闭  1 打开
     * @return 返回值 0：开灯/关灯成功； 非0：失败（-1：串口没打开）
     */
    int simIssCardLight(int iMode);

    /**
     * 检测是否有人接近
     *
     * @return 返回值 -1：串口没打开； -2：此设备无此灯； 0：没人； 1：有人； 非（-1，0，1）：其他错误
     */
    int checkClose();

    /**
     * 关闭串口
     *
     * @return 返回值 0：关闭串口成功； 非（0）：关闭串口失败
     */
    int closeDevice();


}
