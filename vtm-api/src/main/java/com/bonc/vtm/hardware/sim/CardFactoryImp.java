package com.bonc.vtm.hardware.sim;

import android.content.Context;
import android.util.Log;

import com.bonc.vtm.Constants;
import com.bonc.vtm.VtmEquipment;
import com.bonc.vtm.bean.AuthenticationResultBean;
import com.bonc.vtm.bean.RandomBean;
import com.bonc.vtm.bean.ReadCardResultBean;
import com.bonc.vtm.bean.WriteCardParamsBean;
import com.bonc.vtm.bean.WriteCardResultBean;
import com.bonc.vtm.encryption.DES;
import com.cwtech.isssimcard.IssSimCardDri;
import com.google.gson.Gson;
import com.hengbao.rps.writecard.impl.WriteCardImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : CardFactoryImp
 */

public class CardFactoryImp implements CardFactory {

    private static final String TAG = "CardFactoryImp";

/*
    出口参数（pExeInfo）
        000|描述
            第一位
                0 表示设备执行成功
            第二三位
                00 读写卡模块内部无卡
                01 卡在卡口
                02 卡在读写卡模块内部
        100|描述
            第一位
                1 表示设备执行失败
            第二三位
                00 设备执行的错误码，
            |后面
                错误描述
    返回值 0，成功； !=0，失败，通过行数GetLastError 获取错误描述

**********************************************

    0	正常、调用成功
    1	打开串口失败
    4	串口没有打开
    5	读取数据失败
    13	参数错误
    15	超时
    18	CRC校验错误
    19	命令执行失败
    20	日志文件打开失败
    21	接收ACK 失败

**********************************************

*/

    private int openDevice, numFlag = 0;
    //    private String IMEI, vender;
    private IssSimCardDri issSimCardDri;
    private WriteCardImpl writeCardImpl;

    public CardFactoryImp() {
//        this.IMEI = EquipmentInfoBean.IMEI;
//        this.vender = EquipmentInfoBean.deviceVender;
        this.issSimCardDri = new IssSimCardDri();
        this.writeCardImpl = new WriteCardImpl(VtmEquipment.Supplier.ZW);
        this.openDevice = openPort();
    }

    // 打开串口
    private int openPort() {//0：成功； !=0：失败
        return issSimCardDri.Sim_OpenDevice(VtmEquipment.Port.ttysWK0, VtmEquipment.BaudRate.B9600);// 串口号，波特率；
    }

    // 关闭串口
    private int closePort() {
        return issSimCardDri.Sim_CloseDevice();//0：成功； !=0：失败
    }

    // 卡上电
    private int powerOn() {
        byte[] bAtrData = new byte[64];//返回上电数据
        int[] bAtrDataLen = new int[1];//返回上电数据的长度
        byte[] bExeInfo = new byte[64];//出口参数
        return issSimCardDri.Sim_PowerOn(bAtrData, bAtrDataLen, bExeInfo);
    }

    // 卡下电
    private int powerOff() {
        byte[] bExeInfo = new byte[64];
        return issSimCardDri.Sim_PowerOff(bExeInfo);
    }

    // 使卡口能进卡
    private int enableInput() {
        byte[] bExeInfo = new byte[64];
        return issSimCardDri.Sim_EnableInput(bExeInfo);
    }

    // 卡口禁止进卡
    private int disableInsert() {
        byte[] bExeInfo = new byte[64];
        return issSimCardDri.Sim_DisableInsert(bExeInfo);
    }

    // 读取ICCID
    private String readICCID() {
        byte[] bRecvData = new byte[64];//ICCID值/length:21
        int[] iOutDataLen = new int[1];//返回状态信息长度
        byte[] bExeInfo = new byte[64];//出口参数执行状态
        int iRet = issSimCardDri.Sim_ReadICCID(bRecvData, iOutDataLen, bExeInfo);
        if (iRet == 0) {
            return new String(bRecvData).trim();
        } else {
            return null;
        }
    }

    // 重置
    @Override
    public int resetDevice() {
        if (openDevice == 0) {
            byte[] bExeInfo = new byte[64];
            int iRet = issSimCardDri.Sim_ResetDevice(2, bExeInfo);// 1：不移动SIM卡； 2：回收； 3：吐卡
            Log.d(TAG, ">>>>>>>>>>>> resetDevice:" + iRet);
            return iRet;
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    // 卡片位置状态
    @Override
    public String getDevStatus() {
        if (openDevice == 0) {
/*
            0 (0x30)：卡片在前端不夹卡位置。
            1 (0x31)：卡片在前端夹卡位置。
            2 (0x32)：卡片在读卡机射频卡位置。
            3 (0x33)：卡片在IC卡位置。
            4 (0x34)：卡片在后端夹卡位置。
            5 (0x35)：读卡机内无卡。
            6 (0x36)：卡不在标准位置。
            7 (0x37)：卡在重读卡位置。
*/
            byte[] bRecvData = new byte[12];//详细设备状态信息
            int[] iOutDataLen = new int[1];//返回状态信息长度
            byte[] bExeInfo = new byte[64];//出口参数
            int iRet = issSimCardDri.Sim_GetDevStatus(bRecvData, iOutDataLen, bExeInfo);
            Log.e("Log", ">>>>>>>>>>>>>>> bRecvData:" + new String(bRecvData).trim());
            if (iRet == 0) {
                return new String(bRecvData).trim();
            } else {
                Log.d(TAG, ">>>>>>>>>>>>>>> getDevStatus:" + new String(bExeInfo).trim());
                return String.valueOf(Constants.EXCEPTION_NO_FLAG);
            }
        } else {
            return String.valueOf(Constants.EXCEPTION_FLAG);
        }
    }

    // 发卡箱状态
    @Override
    public String getCardBoxStatus() {
        if (openDevice == 0) {
            //六个字节分别是：卡箱1 回收箱1 卡箱2 回收箱2 卡箱3 回收箱3
            //        发卡箱：  0 无卡  1 卡少  2 卡足  @ 卡箱未放置
            //        回收箱：  0 无卡  1 有卡  2 卡满  @ 卡箱未放置
            byte[] bRecvData = new byte[6];
            int[] iOutDataLen = new int[1];
            byte[] bExeInfo = new byte[64];
            int iRet = issSimCardDri.Sim_GetCardBoxStatus(bRecvData, iOutDataLen, bExeInfo);
            if (iRet == 0) {
                String boxStatusString = new String(bRecvData).trim();
                Log.d(TAG, ">>>>>>>>>>>>>>> boxStatusString:" + boxStatusString);
                return boxStatusString;
            } else {
                Log.d(TAG, ">>>>>>>>>>>>>>> getCardBoxStatus:" + new String(bExeInfo).trim());
                return String.valueOf(Constants.EXCEPTION_NO_FLAG);
            }
        } else {
            return String.valueOf(Constants.EXCEPTION_FLAG);
        }
    }

    // 发卡---白卡
    @Override
    public int dispenseBlank(int boxNum) {
        if (numFlag < 3) {
            if (openDevice == 0) {
                numFlag++;
                char boxChar;// 发卡箱： 0 无卡  1 卡少  2 卡足  @ 卡箱未放置
                String boxStatusString = getCardBoxStatus();
                char[] boxStatusCharArray = boxStatusString.toCharArray();
                switch (boxNum) {
                    case 1:
                        boxChar = boxStatusCharArray[0];
                        if (boxChar == '1' || boxChar == '2') {
                            byte[] bExeInfo = new byte[64];
                            return issSimCardDri.Sim_Dispense(boxNum, bExeInfo);
                        } else {
                            dispenseBlank(2);
                        }
                        break;
                    case 2:
                        boxChar = boxStatusCharArray[2];
                        if (boxChar == '1' || boxChar == '2') {
                            byte[] bExeInfo = new byte[64];
                            return issSimCardDri.Sim_Dispense(boxNum, bExeInfo);
                        } else {
                            dispenseBlank(3);
                        }
                        break;
                    case 3:
                        boxChar = boxStatusCharArray[4];
                        if (boxChar == '1' || boxChar == '2') {
                            byte[] bExeInfo = new byte[64];
                            return issSimCardDri.Sim_Dispense(boxNum, bExeInfo);
                        } else {
                            dispenseBlank(1);
                        }
                        break;
                    default:
                        break;
                }
                return Constants.EXCEPTION_FLAG;
            } else {
                return Constants.EXCEPTION_FLAG;
            }
        } else {
            return Constants.EXCEPTION_NO_FLAG;
        }
    }

    // 发卡---预制卡
    @Override
    public int dispensePrefabricate(int boxNum) {
        if (openDevice == 0) {
            char boxChar = '0';// 发卡箱： 0 无卡  1 卡少  2 卡足  @ 卡箱未放置
            String boxStatusString = getCardBoxStatus();
            char[] boxStatusCharArray = boxStatusString.toCharArray();
            switch (boxNum) {
                case 1:
                    boxChar = boxStatusCharArray[0];
                    break;
                case 2:
                    boxChar = boxStatusCharArray[2];
                    break;
                case 3:
                    boxChar = boxStatusCharArray[4];
                    break;
                default:
                    break;
            }
            if (boxChar == '1' || boxChar == '2') {// 发卡箱： 0 无卡  1 卡少  2 卡足  @ 卡箱未放置
                byte[] bExeInfo = new byte[64];
                return issSimCardDri.Sim_Dispense(boxNum, bExeInfo);
            } else {
                if (boxChar == '0') {
                    return Constants.EXCEPTION_NO_FLAG;
                } else {
                    return Constants.NO_CARD_BOX;
                }
            }
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    // 恒宝读卡
    @Override
    public String readCard() {
        if (openDevice == 0) {
            String cardStatus = getDevStatus();
            if ("50".equals(cardStatus)) {
                return String.valueOf(Constants.NO_CARD_BOX);
            } else {
                String cardInfo = writeCardImpl.readCardApduBusiness(VtmEquipment.Supplier.ZW);
                ReadCardResultBean readCardResultBean = new Gson().fromJson(cardInfo, ReadCardResultBean.class);
                String retCode = readCardResultBean.getRetCode();
                if ("C0000".equals(retCode)) {
                    return readCardResultBean.getICCID();
                } else {
                    Log.d(TAG, ">>>>>>>>>>>>>>> HB ReadCard Error Result:" + readCardResultBean.getRetMsg());
                    return String.valueOf(Constants.EXCEPTION_NO_FLAG);
                }
            }
        } else {
            return String.valueOf(Constants.EXCEPTION_FLAG);
        }
    }

    //恒宝写卡
    @Override
    public WriteCardResultBean writeCard(String ciphertext, Context context) {
        if (openDevice == 0) {
            String cardStatus = getDevStatus();
            if ("5".equals(cardStatus)) {
                WriteCardResultBean writeCardResultBean = new WriteCardResultBean();
                writeCardResultBean.setRetCode(String.valueOf(Constants.NO_CARD_BOX));
                return writeCardResultBean;
            } else {
                String randNum = writeCardImpl.getRandNum();//随机数Json
                Log.d(TAG, ">>>>>>>>>>>>>>> randNum:" + randNum);//{"randNum":"1C0D9DF1E787A497","retCode":"C0000","retMsg":"操作成功"}
                //随机数Json转换JavaBean
                RandomBean mdmWriteCardBean = new Gson().fromJson(randNum, RandomBean.class);
                String randNumStr = mdmWriteCardBean.getRandNum();
                Log.d(TAG, ">>>>>>>>>>>>>>> randNumStr:" + randNumStr);
                //3DES-ECB加密随机数
                String encodeKey = DES.des3(randNumStr, "12345678123456781234567812345678", 0);
                Log.d(TAG, ">>>>>>>>>>>>>>> encodeKey:" + encodeKey);//54E28F5C6498B35D
                //鉴权
                Map<String, String> authParamMap = new HashMap<>();
                authParamMap.put("authCode", encodeKey);
                String encodeKeyJson = new Gson().toJson(authParamMap);
                String authentication = writeCardImpl.authentication(encodeKeyJson);//鉴权结果Json
                Log.d(TAG, ">>>>>>>>>>>>>>> authentication:" + authentication);//{"retCode":"C0000","retMsg":"操作成功"}
                //鉴权结果Json转换为对象，并取出状态值判断是否鉴权成功
                AuthenticationResultBean authenticationResultBean = new Gson().fromJson(authentication, AuthenticationResultBean.class);
                String retCode = authenticationResultBean.getRetCode();
                if ("C0000".equals(retCode)) {
                    WriteCardParamsBean writeCardParamsBean = new WriteCardParamsBean();
                    writeCardParamsBean.setCardData(ciphertext);//写卡数据密文
                    writeCardParamsBean.setVendorCode(VtmEquipment.Supplier.ZW);//Constants.EQUIPMENT_FLAG_ZR //设备型号编码 VTM-ZW01 兆维 VTM-ZD01 中鼎 VTM-ZR01 卓睿
                    writeCardParamsBean.setAccount("123456");//设备meid
                    writeCardParamsBean.setAppkey("app1709128285087707-1");//Mdm提供的授权KEYMdm提供的授权KEY，应用app对应账号的密码
                    writeCardParamsBean.setPin("VTM-2017");//Pin密码
                    String wcpJson = new Gson().toJson(writeCardParamsBean);
                    //写卡
                    String writeCardResult = writeCardImpl.writeCardBusiness(wcpJson, context);
                    return new Gson().fromJson(writeCardResult, WriteCardResultBean.class);
                } else {
                    WriteCardResultBean writeCardResultBean = new WriteCardResultBean();
                    writeCardResultBean.setRetCode(String.valueOf(Constants.EXCEPTION_NO_FLAG));
                    return writeCardResultBean;
                }
            }
        } else {
            WriteCardResultBean writeCardResultBean = new WriteCardResultBean();
            writeCardResultBean.setRetCode(String.valueOf(Constants.EXCEPTION_FLAG));
            return writeCardResultBean;
        }
    }

    // 吐卡---把卡从读写卡位置送到发发卡机卡口
    @Override
    public int eject() {
        if (openDevice == 0) {
            byte[] bExeInfo = new byte[64];
            return issSimCardDri.Sim_Eject(bExeInfo);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    // 回收卡（吞卡）---把卡从读写卡位置回收到1回收盒（前回收仓）
    @Override
    public int capture() {
        if (openDevice == 0) {
            byte[] bExeInfo = new byte[64];
            return issSimCardDri.Sim_Capture(1, bExeInfo);//iBoxIndex 1：前回收仓； 2：侧回收仓
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    // 持卡位回收---把卡从卡口位置回收到1回收盒（前回收仓）
    @Override
    public int intake() {
        if (openDevice == 0) {
            byte[] bExeInfo = new byte[64];
            return issSimCardDri.Sim_ReIntake(1, bExeInfo);//iBoxIndex 1：前回收仓； 2：侧回收仓
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    // 30秒内轮询卡状态直至时间结束或者用户将卡取走，当时间满30秒后回收卡
    @Override
    public int reIntake() {
        if (openDevice == 0) {
            String waitTimeStr = null;
            ExecutorService exs = Executors.newCachedThreadPool();
            Future<String> fs = exs.submit(new CallThread());
            try {
                waitTimeStr = fs.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (waitTimeStr != null && String.valueOf(Constants.NO_CARD_BOX).equals(waitTimeStr)) {
                byte[] bExeInfo = new byte[64];
                issSimCardDri.Sim_ReIntake(1, bExeInfo);// 前回收盒
            }
            return Integer.valueOf(waitTimeStr);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    //内部类轮询卡状态
    class CallThread implements Callable<String> {
        /*
                   0 (0x30)：卡片在前端不夹卡位置。
                   1 (0x31)：卡片在前端夹卡位置。
                   2 (0x32)：卡片在读卡机射频卡位置。
                   3 (0x33)：卡片在IC卡位置。
                   4 (0x34)：卡片在后端夹卡位置。
                   5 (0x35)：读卡机内无卡。
                   6 (0x36)：卡不在标准位置)。
                   7 (0x37)：卡在重读卡位置。
       */
        @Override
        public String call() throws Exception {
            boolean statusFlag = true;
            long startTime = new Date().getTime();
            String waitTimeStr = null;
            do {
                long endTime = new Date().getTime();
                long waitTime = endTime - startTime;
                Log.d(TAG, ">>>>>>>>>>>>>>> startTime:" + startTime + "--->endTime" + endTime);
                if (waitTime < 30 * 1000) {
                    String cardStatus = getDevStatus();
                    if ("5".equals(cardStatus)) {
                        statusFlag = false;
//                        waitTimeStr = String.valueOf(waitTime / 1000) + "-1";// 被取走
                        waitTimeStr = String.valueOf(Constants.EXCEPTION_NO_FLAG);// 卡被取走
                    } else if ("1".equals(cardStatus)) {
                        statusFlag = true;
                        Thread.sleep(2000);
                    }
                } else {
                    statusFlag = false;
//                    waitTimeStr = String.valueOf(waitTime / 1000) + "-0";// 30S计时
                    waitTimeStr = String.valueOf(Constants.NO_CARD_BOX);// 达到30S计时
                }
            } while (statusFlag);
            return waitTimeStr;
        }
    }
}
