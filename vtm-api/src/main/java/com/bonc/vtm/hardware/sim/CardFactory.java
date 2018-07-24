package com.bonc.vtm.hardware.sim;

import android.content.Context;

import com.bonc.vtm.bean.WriteCardResultBean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : CardFactory
 */

public interface CardFactory {

    /**
     * 应用启动时卡机重置，如果都卡位有卡片则吞卡
     *
     * @return iRet 返回值 0：成功； -1：串口没打开
     */
    int resetDevice();


    /**
     * 卡片位置状态
     *
     * @return cardInfoString 返回值【 -1：串口没打开； -2：获取卡片位置状态失败； !=-1&&!= -2：返回即是卡片位置状态信息】
     * <p>
     * 0 (0x30)：卡片在前端不夹卡位置。
     * 1 (0x31)：卡片在前端夹卡位置。
     * 2 (0x32)：卡片在读卡机射频卡位置。
     * 3 (0x33)：卡片在IC卡位置。
     * 4 (0x34)：卡片在后端夹卡位置。
     * 5 (0x35)：读卡机内无卡。
     * 6 (0x36)：卡不在标准位置。
     * 7 (0x37)：卡在重读卡位置。
     */
    String getDevStatus();


    /**
     * 发卡箱状态
     *
     * @return boxStatusString 返回值【 -1：串口没打开； -2：获取发卡箱状态失败； !=-1&&!= -2：返回即是发卡箱状态信息】
     * <p>
     * 六个字节分别是：卡箱1 回收箱1 卡箱2 回收箱2 卡箱3 回收箱3
     * <p>
     * 发卡箱： 0 无卡  1 卡少  2 卡足  @ 卡箱未放置
     * 回收箱： 0 无卡  1 有卡  2 卡满  @ 卡箱未放置
     */
    String getCardBoxStatus();


    /**
     * 白卡发卡
     *
     * @return iRet 返回值【 0：发卡成功； -1：串口没打开； -2：三个卡箱均无卡； 15：超时； 19：命令执行失败 】
     */
    int dispenseBlank(int boxNum);


    /**
     * 预制卡发卡
     *
     * @return iRet 返回值【 0：发卡成功； -1：串口没打开； -2：当前卡箱无卡； -3：卡箱未放置； 15：超时； 19：命令执行失败 】
     */
    int dispensePrefabricate(int boxNum);


    /**
     * 读卡
     *
     * @return ICCID 返回值【 -1：串口没打开； -2：读卡失败； -3：读卡时读卡位无卡； 】
     */
    String readCard();


    /**
     * 写卡
     *
     * @param ciphertext 密文
     * @param context    调用此函数的上下文环境
     * @return 返回对象中的retCode属性值【 C0000：写卡成功； -1：串口没打开； -2：鉴权失败； -3：写卡时卡机内无卡；】
     */
    WriteCardResultBean writeCard(String ciphertext, Context context);


    /**
     * 吐卡
     *
     * @return iRet 返回值 0：成功； -1：串口没打开
     */
    int eject();


    /**
     * 卡回收-从读写卡位置回收
     *
     * @return iRet 返回值 0：成功； -1：串口没打开
     */
    int capture();


    /**
     * 卡回收-从卡口位置回收
     *
     * @return iRet 返回值 0：成功； -1：串口没打开
     */
    int intake();


    /**
     * 30秒内轮询卡状态直至时间结束或者用户将卡取走，当时间满30秒后回收卡（卡回收-从卡口位置回收）
     *
     * @return iRet 返回值【 -1：串口没打开； -2：卡被取走； -3：卡被回收； 15：超时； 19：命令执行失败 】
     */
    int reIntake();

}
