package com.bonc.vtm;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 常量类
 */

public class Constants {

    public static final byte CARD_30 = 0x30;//1.将卡移动到出卡口(持卡位); 2.复位移动持卡位;
    public static final byte CARD_31 = 0x31;//1.移动到IC卡位; 2.复位回收;
    public static final byte CARD_32 = 0x32;//1.将卡移动到RF卡位(射频位/读卡位);
    public static final byte CARD_33 = 0x33;//1.将卡回收到回收盒中; 2.复位无动作;
    public static final byte CARD_39 = 0x39;//1.前端弹出卡片到出卡口(不持卡位);


    /**
     * 1、逻辑执行异常标记/
     * 2、卡机串口未打开
     */
    public static final int EXCEPTION_FLAG = -1;

    /**
     * 1、设备无此指示灯/
     * 2、卡机卡箱无卡/
     * 3、卡被取走/
     * 4、读卡失败/
     * 5、获取发卡箱状态失败/
     * 6、获取卡片位置状态失败/
     * 7、写卡鉴权失败
     */
    public static final int EXCEPTION_NO_FLAG = -2;

    /**
     * 1、卡机卡箱未放置/
     * 2、30秒计时时间到/
     * 3、读卡时卡机内无卡/
     * 4、写卡时卡机内无卡
     */
    public static final int NO_CARD_BOX = -3;

}
