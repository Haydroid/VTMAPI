package com.bonc.vtm.hardware.sim;

import com.bonc.vtm.bean.WriteCardResultBean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : CardFactoryResultResolver
 */

public class CardFactoryResultResolver {


    /**
     * 预制卡-发卡结果转换
     * <p>
     * 0：发卡成功； -1：串口没打开； -2：当前卡箱无卡； -3：卡箱未放置；
     * -4：此设备无此指令接口； 15：超时； 19：命令执行失败
     * <p>
     *
     * @return 结果描述
     */
    public static String dispensePreCardResult2FailInfo(int result) {

        switch (result) {
            case 0:
                return "";
            case -1:
                return "串口没打开";
            case -2:
                return "当前卡箱无卡";
            case -3:
                return "卡箱未放置";
            case -4:
                return "此设备无此指令接口";
            case -15:
                return "超时";
            case 19:
                return "命令执行失败";
            default:
                return "未知错误";
        }

    }

    /**
     * 白卡-发卡结果转换
     * <p>
     * 0：发卡成功； -1：串口没打开； -2：三个卡箱均无卡；
     * -4：此设备无此指令接口； 15：超时； 19：命令执行失败
     * <p>
     *
     * @return 结果描述
     */
    public static String dispenseBlankResult2FailInfo(int result) {

        switch (result) {
            case 0:
                return "";
            case -1:
                return "串口没打开";
            case -2:
                return "当前卡箱无卡";
            case -4:
                return "此设备无此指令接口";
            case -15:
                return "超时";
            case 19:
                return "命令执行失败";
            default:
                return "未知错误";
        }

    }

    /**
     * 预制卡-读卡结果转换
     * <p>
     * -1：串口没打开； -2：读卡失败； -3：读卡时读卡位无卡； -4：此设备无此指令接口；
     *
     * @return 结果描述
     */
    public static String readCardResult2FailInfo(String result) {

        switch (result) {
            case "0":
                return "";
            case "-1":
                return "串口没打开";
            case "-2":
                return "读卡失败";
            case "-3":
                return "读卡时读卡位无卡";
            case "-4":
                return "此设备无此指令接口";
            default:
                return "未知错误";
        }

    }

    /**
     * 白卡-写卡结果转换
     * <p>
     * C0000：写卡成功； -1：串口没打开； -2：；
     * -3：写卡时卡机内无卡； -4：此设备无此指令接口；
     *
     * @return 结果描述
     */
    public static String writeCardResult2FailInfo(WriteCardResultBean writeCardResult) {

        String retCode = writeCardResult.getRetCode();

        switch (retCode) {
            case "C0000":
                return "";
            case "C0001":
                return "业务处理异常";
            case "C0003":
                return "报文解密失败";
            case "C0004":
                return "写卡数据解密失败";
            case "C0005":
                return "获取设备厂商失败";
            case "-1":
                return "串口没打开";
            case "-2":
                return "鉴权失败";
            case "-3":
                return "写卡时卡机内无卡";
            case "-4":
                return "此设备无此指令接口";
            default:
                return "未知错误";
        }

    }


    /**
     * 通用卡机调用结果转换（使用：卡机重置、吐卡、吞卡、）
     * <p>
     * -1：串口没打开； -2：读卡失败； -3：读卡时读卡位无卡； -4：此设备无此指令接口；
     *
     * @return 结果描述
     */
    public static String commResult2FailInfo(int result) {

        switch (result) {
            case 0:
                return "";
            case -1:
                return "串口没打开";
            case -4:
                return "此设备无此指令接口";
            default:
                return "未知错误";
        }

    }


    /**
     * 根据写卡结果，判断写卡是否成功
     *
     * @param writeCardResult
     * @return
     */
    public static boolean isWriteCardSucceed(WriteCardResultBean writeCardResult) {

        String retCode = writeCardResult.getRetCode();
        return (retCode != null && "C0000".equals(retCode));
    }


}
