package com.bonc.vtm.hardware.card;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.widget.TextView;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : comm
 */

public class comm {
    public static final int F5_E_BOTH_COMM_FAILURE = -114;
    public static final int F5_E_BUFFER_TOO_SMALL = -32;
    public static final int F5_E_CANCELED = -10;
    public static final int F5_E_CARDBOX_EMPTY = -120;
    public static final int F5_E_CARD_JAMMED = -105;
    public static final int F5_E_CARD_LOCKED = -111;
    public static final int F5_E_CARD_NOT_PRESENT = -109;
    public static final int F5_E_COMMAND_FAILURE = -102;
    public static final int F5_E_DEV_NOT_READY = -2;
    public static final int F5_E_DISABLED_COMMAND = -101;
    public static final int F5_E_ES = -126;
    public static final int F5_E_FORMAT_ERROR = -113;
    public static final int F5_E_INTERNAL_ERROR = -23;
    public static final int F5_E_INVALID_ARG = -31;
    public static final int F5_E_INVALID_HANDLE = -30;
    public static final int F5_E_INVALID_PASSWORD = -112;
    public static final int F5_E_LANE_ABNORMAL = -115;
    public static final int F5_E_LRC = -127;
    public static final int F5_E_MOTOR_OVERLOAD = -122;
    public static final int F5_E_NO_RETRY = -124;
    public static final int F5_E_OUT_OF_MEMORY = -24;
    public static final int F5_E_PARITY = -123;
    public static final int F5_E_PORT_NOT_AVAIL = -1;
    public static final int F5_E_POWER_ABNORMAL = -103;
    public static final int F5_E_RETAINBIN_FULL = -121;
    public static final int F5_E_SENSOR_ABNORMAL = -104;
    public static final int F5_E_SHUTTER_ABNORMAL = -106;
    public static final int F5_E_SS = -125;
    public static final int F5_E_TIMEOUT = -12;
    public static final int F5_E_TOO_LONG_CARD = -107;
    public static final int F5_E_TOO_SHORT_CARD = -108;
    public static final int F5_E_UNKNOWN_ERROR = -22;
    public static final int F5_E_UNSUPP_COMMAND = -100;
    public static int LINK = 4;
    public static UsbManager manager;
    public static TextView nOutputText;
    public static Context usbCont;

    public void ErrorMsg(int error) throws Exception {
        switch (error) {
            case F5_E_LRC /*-127*/:
                throw new Exception("LRC出错。");
            case F5_E_ES /*-126*/:
                throw new Exception("ES出错。");
            case F5_E_SS /*-125*/:
                throw new Exception("SS出错。");
            case F5_E_NO_RETRY /*-124*/:
                throw new Exception("to show  that  only  SS,ES,LRC are contained in the track(no retry)  。");
            case F5_E_PARITY /*-123*/:
                throw new Exception("奇偶校验出错。");
            case F5_E_MOTOR_OVERLOAD /*-122*/:
                throw new Exception("电机过载。");
            case F5_E_RETAINBIN_FULL /*-121*/:
                throw new Exception("回收箱满 ");
            case F5_E_CARDBOX_EMPTY /*-120*/:
                throw new Exception("发卡箱空");
            case F5_E_LANE_ABNORMAL /*-115*/:
                throw new Exception("移动通道异常");
            case F5_E_BOTH_COMM_FAILURE /*-114*/:
                throw new Exception("发卡模块和读卡模块通信失败 ");
            case F5_E_FORMAT_ERROR /*-113*/:
                throw new Exception("通信端口: COM%d, 波特率: %d bps");
            case F5_E_INVALID_PASSWORD /*-112*/:
                throw new Exception("提供的认证密码不正确 ");
            case F5_E_CARD_LOCKED /*-111*/:
                throw new Exception("卡片已报废");
            case F5_E_CARD_NOT_PRESENT /*-109*/:
                throw new Exception("IC位或射频位无卡");
            case F5_E_TOO_SHORT_CARD /*-108*/:
                throw new Exception("插入到机内的卡片太短");
            case F5_E_TOO_LONG_CARD /*-107*/:
                throw new Exception("插入到机内的卡片太长");
            case F5_E_SHUTTER_ABNORMAL /*-106*/:
                throw new Exception("闸门异常");
            case F5_E_CARD_JAMMED /*-105*/:
                throw new Exception("卡片堵塞");
            case F5_E_SENSOR_ABNORMAL /*-104*/:
                throw new Exception("传感器异常");
            case F5_E_POWER_ABNORMAL /*-103*/:
                throw new Exception("供电电源异常");
            case F5_E_COMMAND_FAILURE /*-102*/:
                throw new Exception("命令执行失败");
            case F5_E_DISABLED_COMMAND /*-101*/:
                throw new Exception("命令不能在当前的状态下执行");
            case F5_E_UNSUPP_COMMAND /*-100*/:
                throw new Exception("未定义的命令");
            case F5_E_BUFFER_TOO_SMALL /*-32*/:
                throw new Exception("接收数据的缓冲区太小");
            case F5_E_INVALID_ARG /*-31*/:
                throw new Exception("提供的一个或多个参数无效");
            case F5_E_INVALID_HANDLE /*-30*/:
                throw new Exception("提供的句柄无效");
            case F5_E_OUT_OF_MEMORY /*-24*/:
                throw new Exception("没有足够的内存来完成当前的操作");
            case F5_E_INTERNAL_ERROR /*-23*/:
                throw new Exception("接收数据异常");
            case F5_E_UNKNOWN_ERROR /*-22*/:
                throw new Exception("内部错误");
            case F5_E_TIMEOUT /*-12*/:
                throw new Exception("通信超时");
            case F5_E_CANCELED /*-10*/:
                throw new Exception("当前的操作被强止中断");
            case F5_E_DEV_NOT_READY /*-2*/:
                throw new Exception("设备未就绪");
            case F5_E_PORT_NOT_AVAIL /*-1*/:
                throw new Exception("指定的端口不存在或者被其它程序占用");
            default:
                throw new Exception("命令失败");
        }
    }
}
