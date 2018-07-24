package com.bonc.vtm.hardware.card;

import android.util.Log;

import com.bonc.vtm.utils.HexUtil;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : F3-1300 CardDriver
 */

public class CardDriver {

    private static int LINK = -1;
    private static final String TAG = "CardDriver";

    public class CardStatus {
        public byte cardPosition; //卡片位置 : 机内无卡(0x30), 卡在出卡口位(0x31),机内有卡(0x32)
        public byte cardBoxStatus;//卡箱状态: 卡箱空(0x30), 卡箱卡少(0x31),卡箱卡足(0x32)
        public boolean isCaptureBoxFull;//回收箱状态： 满（true）,未满（false）
    }

    private class ReceiveData {
        public byte[] buffer;
        public int ReviceDataLen;
    }

    private native static int csConnect(String portName, int baudRate) throws Exception;

    private native static void csDisconnect(int handle) throws Exception;

    private native static void csExecute(int handle, byte[] cmpData, int timeOut, ReceiveData resData, int rdtOffset, boolean bStatus) throws Exception;

    private native static void csExecuteEncode(int handle, byte[] cmpData, int timeOut, ReceiveData resData) throws Exception;

    static {
        System.loadLibrary("ACT-F3-SIMCard-jni");
    }

    public CardDriver() {
    }

    /**
     * 01、串口连接
     *
     * @参数1：portName 串口名称 如： ttysWK0
     * @参数2：baudRate 波特率 默认9600
     * @返回值：成功返回true 失败返回false
     */
    public boolean connect(String portName, int baudRate) throws Exception {
        CardDriver Rs232Connect = new CardDriver();
        LINK = Rs232Connect.csConnect(portName, baudRate);
        Log.i(TAG, String.format("\nportName = %s\nbaudRate = %d\nLINK = %d", portName, baudRate, LINK));
        if (LINK == 0) return true;
        return false;
    }

    /**
     * 02、断开连接
     *
     * @参数：无
     * @返回值：无 ，失败抛出异常提示
     */
    public void disconnect() throws Exception {
        if (LINK == 0) csDisconnect(LINK);
        LINK = -1;
    }

    /**
     * 03、复位设备，并返回固件版本号
     *
     * @参数1：action 复位设备的方式，有：
     * RESET_NOACTION(0x33) 复位无动作
     * RESET_FRONT_CARD(0x30) 复位移动卡到前端
     * RESET_RETURN_CARD(0x31) 复位回收卡片
     * @返回值：成功返回设备固件版本，失败抛出异常提示
     */
    public String reset(byte cmdByte) throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x30, cmdByte};
//        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
        return new String(execute(bd, resData, 0, false));
    }

    /**
     * 04、查询卡机状态
     *
     * @参数：无
     * @返回值：成功返回值 CardStatus卡机状态值，如下：
     * cardPosition 卡片位置 : 机内无卡(0x30), 卡在出卡口位(0x31),机内有卡(0x32)
     * cardBoxStatus 卡箱状态: 卡箱空(0x30), 卡箱卡少(0x31),卡箱卡足(0x32)
     * isCaptureBoxFull 回收箱状态： 满（true）,未满（false）
     * 失败抛出异常提示
     */
    public CardStatus getCardPosition() throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x31, 0x30};
        CardStatus values = new CardStatus();
        resData.buffer = execute(bd, resData, 0, true);
        values.cardPosition = resData.buffer[0];
        values.cardBoxStatus = resData.buffer[1];
        if (resData.buffer[2] == '0')
            values.isCaptureBoxFull = false;
        if (resData.buffer[2] == '1')
            values.isCaptureBoxFull = true;
        return values;
    }

    /**
     * 05、卡片移动操作
     *
     * @参数1：method卡片移动的方式 ，有如下值：
     * MOVE_TOCARDOUT(0x39)  前端弹出卡片
     * MOVE_TOFRONTEND(0x30) 前端出卡口
     * MOVE_TORFREAD(0x32) 射频位
     * CAPTURE_TO_BOX(0x33) 回收
     * MOVE_TOICCPOS(0x31) 移动IC位
     * @返回值：无 失败抛出异常提示
     */
    public String moveCard(byte method) throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x32, method};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 06、控制卡口是否能进卡
     *
     * @参数：允许出卡口进卡:0x30; 2.禁止出卡口进卡:0x31
     * @返回值：无，失败抛出异常提示
     */
    public String controlInsertion(byte parameter) throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x33, parameter};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 07、IC卡/RF卡类型 检测
     *
     * @参数：pm 自动检测IC卡类型:0x30; 自动检测RF卡类型:0x31
     * @返回值：无 失败抛出异常提示
     */
    public String checkCardType(byte pm) throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x50, pm};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 08、CPU卡上电 / CPU卡冷复位
     *
     * @参数：无
     * @返回值：成功返回ATR ，失败抛出异常
     */
    public byte[] chipPowerOn() throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x51, 0x30, 0x30, 0x30};
        bd = execute(bd, resData, 0, false);
        byte[] atr = new byte[bd.length - 2];
        System.arraycopy(bd, 2, atr, 0, bd.length - 2);
        return atr;
    }

    /**
     * 09、CPU卡下电
     *
     * @参数：无
     * @返回值：成功返回ATR ，失败抛出异常
     */
    public String chipPowerOff() throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x51, 0x31};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 10、CPU卡状态查询
     *
     * @参数：无
     * @返回值：成功返回ATR ，失败抛出异常
     */
    public String chipCardStatus() throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{0x51, 0x32};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 11、APDU数据交换 / T=0协议CPU卡APDU数据交换
     *
     * @参数1：inData 需要下发APDU命令
     * @返回值：成功返回PDU命令响应 ，失败抛出异常
     */
    public byte[] chipIo(byte[] inData) throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] RBuf;
        byte[] bd = new byte[]{0x51, 0x33};
        byte[] bc = new byte[inData.length + bd.length];
        System.arraycopy(bd, 0, bc, 0, bd.length);
        System.arraycopy(inData, 0, bc, bd.length, inData.length);
        resData.buffer = execute(bc, resData, 0, false);
        if (resData.buffer.length > 0) {
            RBuf = new byte[resData.buffer.length];
            System.arraycopy(resData.buffer, 0, RBuf, 0, resData.buffer.length);
        } else return null;
        return RBuf;
    }

    /**
     * 12、版本信息读取
     *
     * @参数：无
     * @返回值：成功返回ATR ，失败抛出异常
     */
    public String getVersion() throws Exception {
        ReceiveData resData = new ReceiveData();
        byte[] bd = new byte[]{(byte) 0xA4, 0x30};
        return HexUtil.bytesToHexString(execute(bd, resData, 0, false));
    }

    /**
     * 13、命令报文发送
     *
     * @参数1：cmdA 需要下发命令
     * @参数2：resData返回的数据BUF 和长度
     * @参数3：rdtOffset 接收数据的偏移量（备用，默认0）
     * @参数4：bStatus 状态值（备用，默认0）
     * @返回值：成功返回命令响应 ，失败抛出异常
     */
    public byte[] execute(byte[] cmdA, ReceiveData resData, int rdtOffset, boolean bStatus) throws Exception {
        if (LINK == 0) {
            csExecute(LINK, cmdA, 0, resData, rdtOffset, bStatus);
            Log.i("execute", "csExecute:" + HexUtil.bytesToHexString(resData.buffer) + "|==>|csExecuteLen:" + resData.buffer.length);
            if (resData.buffer.length != 0) return resData.buffer;
        }
        return null;
    }
}
