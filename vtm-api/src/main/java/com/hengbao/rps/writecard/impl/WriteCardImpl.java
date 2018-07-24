package com.hengbao.rps.writecard.impl;

import android.content.Context;

import com.Crt.ExternalCall;
import com.cwtech.isssimcard.IssSimCardDri;
import com.google.gson.Gson;
import com.hengbao.rps.BaseBusiness;
import com.hengbao.rps.WriteSIMCardJni;
import com.hengbao.rps.entity.CommonRspInfo;
import com.hengbao.rps.entity.PersonalData;
import com.hengbao.rps.entity.RandNumReq;
import com.hengbao.rps.entity.RandNumResp;
import com.hengbao.rps.entity.ReadCardResp;
import com.hengbao.rps.entity.WriteCardReq;
import com.hengbao.rps.entity.WriteCardResp;
import com.hengbao.rps.exception.ErrorsEnum;
import com.hengbao.rps.utils.DataUtils;
import com.hengbao.rps.utils.LogWriter;
import com.hengbao.rps.utils.StringUtil;
import com.hengbao.rps.utils.XmlMsgAnalysis;
import com.hengbao.rps.writecard.WriteCardInterface;

import org.jdom2.Element;

import java.io.IOException;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 写卡接口实现
 */

public class WriteCardImpl extends BaseBusiness implements WriteCardInterface {
    public static String stringZR = "VTM-ZR";
    public static String stringZD = "VTM-ZD";
    public static String stringZW = "VTM-ZW";
    public static IssSimCardDri zrisscimcard_dri = null;
    public static IssSimCardDri cwisscimcard_dri = null;
    public static ExternalCall zdexternalCall = null;
    public String sVendorCode = "";
    public static Boolean isLog = true;
    public static Boolean isAuth = false;
    static String strEmptyCardSerialNum = "";
    static String strIccidSequence = "";
    public static WriteSIMCardJni writeSIMCardJni = new WriteSIMCardJni();
    public static PersonalData personalData = null;


    public WriteCardImpl(String vendor) {
        int iRet = -1;
        byte[] pExeInfor = new byte[64];
        if (vendor.equals(stringZR)) {
            zrisscimcard_dri = new IssSimCardDri();
        } else if (vendor.equals(stringZW)) {
            cwisscimcard_dri = new IssSimCardDri();
        } else if (vendor.equals(stringZD)) {
            zdexternalCall = new ExternalCall();
        }
    }


    public String writeCardBusiness(String reqParam, Context mcontext) {
        WriteCardResp resp = new WriteCardResp();
        Gson gson = new Gson();

/*
        if (!isAuth) {
            this.packRspInfo(resp, ErrorsEnum.C0007.getCode(), ErrorsEnum.C0007.getMessage());
            return gson.toJson(resp);
        } else
*/

        if (reqParam != null && !"".equals(reqParam)) {
            WriteCardReq req = gson.fromJson(reqParam, WriteCardReq.class);
            String encCardData = req.getCardData();
            String vendorCode = req.getVendorCode();
            String Account = req.getAccount();
            String appkey = req.getAppkey();
            String pin = req.getPin();
            if (encCardData != null && !"".equals(encCardData) && vendorCode != null && !"".equals(vendorCode) && Account != null && !"".equals(Account) && appkey != null && !"".equals(appkey) && pin != null && !"".equals(pin)) {
                this.sVendorCode = vendorCode;
                if (isLog) {
                    LogWriter.getInstance().print("厂商编号：" + vendorCode);
                }

//                String encdata = this.decMsg.decryptMsgBusiness(encCardData, Account, appkey, pin, mcontext);
//                if (encdata == null) {
                if (encCardData == null) {
                    isAuth = false;
                    this.packRspInfo(resp, ErrorsEnum.C0003.getCode(), ErrorsEnum.C0003.getMessage());
                    return gson.toJson(resp);
                } else {
                    if (isLog) {
                        LogWriter.getInstance().print("转换前数据：" + encCardData);
                    }

//                    encdata = encdata.replaceAll("\r\n", "");
                    if (isLog) {
//                        LogWriter.getInstance().print("转换后数据：" + encdata);
                    }

                    if (isLog) {
                        LogWriter.getInstance().print("写卡数据：" + encCardData);
                    }

                    XmlMsgAnalysis xml = new XmlMsgAnalysis();
                    Element root = null;

                    try {
                        root = XmlMsgAnalysis.parseXml(encCardData);
                    } catch (Exception var35) {
                        var35.printStackTrace();
                        isAuth = false;
                    }

                    Element resSvcCont = root.getChild("SvcCont");
                    Element personalData = resSvcCont.getChild("PersonalData");
                    String strWriteCardData = xml.getPersonalInfo(personalData);
                    String ICCID = xml.putPersonInfo("ICCID", personalData);
                    String CIMSI = xml.putPersonInfo("IMSI", personalData);
                    String GIMSI = xml.putPersonInfo("IMSIG", personalData);
                    String USIMIMSI = xml.putPersonInfo("IMSI_LTE", personalData);
                    strEmptyCardSerialNum = this.readCardICCSERIAL(this.sVendorCode);
                    if (isLog) {
                        LogWriter.getInstance().print("卡片序列号：" + strEmptyCardSerialNum);
                    }

                    String strExternData = "";
                    if (strEmptyCardSerialNum == null) {
//                        isAuth = false;
                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                        return gson.toJson(resp);
                    } else {
                        strExternData = strEmptyCardSerialNum.substring(5, 10) + ";" + this.getCardType(this.sVendorCode);
                        String strApdus = writeSIMCardJni.GetWriteCardApdu(1, 1, strWriteCardData, strExternData);
                        if (isLog) {
                            LogWriter.getInstance().print("组件返回写卡指令：" + strApdus);
                        }

                        if (strApdus.equals("")) {
//                            isAuth = false;
                            this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                            return gson.toJson(resp);
                        } else {
                            String closeFlag = strApdus.substring(strApdus.length() - 1);
                            LogWriter.getInstance().print("closeFlag：" + closeFlag);
                            int closeNum = Integer.parseInt(closeFlag);
                            int indexStage = 2 - closeNum;
                            String strRandCmd = "";
                            String strRand = "";
                            String strCloseCardCmd = "";
                            strApdus = strApdus.substring(0, strApdus.length() - 2);
                            String[] strApdu = strApdus.split(";");
                            LogWriter.getInstance().print("strApdu：" + strApdu);
                            int ret = this.sendApduListBusiness(strApdu);
                            if (isLog) {
                                LogWriter.getInstance().print("发卡结果反馈：" + ret);
                            }

                            if (ret != 0) {
                                isAuth = false;
                                this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                return gson.toJson(resp);
                            } else {
                                String respRet;
                                for (int i = 0; i < closeNum; i++) {
                                    respRet = null;
                                    strRandCmd = writeSIMCardJni.CloseCardRandNum();
                                    ret = this.SendCloseCardApdu(strRandCmd, respRet);
                                    if (ret != 0) {
                                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                        return gson.toJson(resp);
                                    }
                                    strRand = respRet;
                                    strCloseCardCmd = writeSIMCardJni.CloseCard(indexStage, respRet);
                                    ret = this.SendCloseCardApdu(strRandCmd, respRet);
                                    if (ret != 0) {
                                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                        return gson.toJson(resp);
                                    }
                                    indexStage++;
//                                    ++indexStage;
                                }

                                String ICCSERIAL = this.readCardICCSERIAL(this.sVendorCode);
                                String ICCSERIAL_4 = ICCSERIAL.substring(ICCSERIAL.length() - 4, ICCSERIAL.length());
//                                respRet = ICCSERIAL.substring(ICCSERIAL.length() - 4, ICCSERIAL.length());
//                                String ICCSERIAL_4_ASCII = StringUtil.HEX_2_ASC(respRet);
                                String ICCSERIAL_4_ASCII = StringUtil.HEX_2_ASC(ICCSERIAL_4);
                                StringBuilder sb = new StringBuilder();
                                sb.append(ICCSERIAL.substring(0, ICCSERIAL.length() - 4));
                                sb.append(ICCSERIAL_4_ASCII);
                                ICCSERIAL = sb.toString();
                                resp.setICCSERIAL(ICCSERIAL);
                                resp.setICCID(ICCID);
                                resp.setCIMSI(CIMSI);
                                resp.setGIMSI(GIMSI);
                                resp.setUSIMIMSI(USIMIMSI);
                                if (isLog) {
                                    LogWriter.getInstance().print("组件返回写卡结果参数：ICCSERIAL->" + ICCSERIAL + "ICCID->" + ICCID + "CIMSI->" + CIMSI + "GIMSI->" + GIMSI + "USIMIMSI->" + USIMIMSI);
                                }

                                isAuth = false;
                                this.packRspInfo(resp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
                                return gson.toJson(resp);
                            }
                        }
                    }
                }
            } else {
                isAuth = false;
                this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
                return gson.toJson(resp);
            }
        } else {
            this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
            return gson.toJson(resp);
        }
    }

/*
    public String writeCardBusiness(String reqParam, Context mcontext) {
        WriteCardResp resp = new WriteCardResp();
        Gson gson = new Gson();
        if (!isAuth) {
            this.packRspInfo(resp, ErrorsEnum.C0007.getCode(), ErrorsEnum.C0007.getMessage());
            return gson.toJson(resp);
        } else if (reqParam != null && !"".equals(reqParam)) {
            WriteCardReq req = (WriteCardReq) gson.fromJson(reqParam, WriteCardReq.class);
            String encCardData = req.getCardData();
            String vendorCode = req.getVendorCode();
            String Account = req.getAccount();
            String appkey = req.getAppkey();
            String pin = req.getPin();
            if (encCardData != null && !"".equals(encCardData) && vendorCode != null && !"".equals(vendorCode) && Account != null && !"".equals(Account) && appkey != null && !"".equals(appkey) && pin != null && !"".equals(pin)) {
                this.sVendorCode = vendorCode;
                if (isLog) {
                    LogWriter.getInstance().print("厂商编号：" + vendorCode);
                }

                String encdata = this.decMsg.decryptMsgBusiness(encCardData, Account, appkey, pin, mcontext);
                if (encdata == null) {
                    isAuth = false;
                    this.packRspInfo(resp, ErrorsEnum.C0003.getCode(), ErrorsEnum.C0003.getMessage());
                    return gson.toJson(resp);
                } else {
                    if (isLog) {
                        LogWriter.getInstance().print("转换前数据：" + encdata);
                    }

                    encdata = encdata.replaceAll("\r\n", "");
                    if (isLog) {
                        LogWriter.getInstance().print("转换后数据：" + encdata);
                    }

                    if (isLog) {
                        LogWriter.getInstance().print("写卡数据：" + encdata);
                    }

                    XmlMsgAnalysis xml = new XmlMsgAnalysis();
                    Element root = null;

                    try {
                        root = XmlMsgAnalysis.parseXml(encdata);
                    } catch (Exception var35) {
                        var35.printStackTrace();
                        isAuth = false;
                    }

                    Element resSvcCont = root.getChild("SvcCont");
                    Element personalData = resSvcCont.getChild("PersonalData");
                    String strWriteCardData = xml.getPersonalInfo(personalData);
                    String ICCID = xml.putPersonInfo("ICCID", personalData);
                    String CIMSI = xml.putPersonInfo("IMSI", personalData);
                    String GIMSI = xml.putPersonInfo("IMSIG", personalData);
                    String USIMIMSI = xml.putPersonInfo("IMSI_LTE", personalData);
                    strEmptyCardSerialNum = this.readCardICCSERIAL(this.sVendorCode);
                    if (isLog) {
                        LogWriter.getInstance().print("卡片序列号：" + strEmptyCardSerialNum);
                    }

                    String strExternData = "";
                    if (strEmptyCardSerialNum == null) {
                        isAuth = false;
                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                        return gson.toJson(resp);
                    } else {
                        strExternData = strEmptyCardSerialNum.substring(5, 10) + ";" + this.getCardType(this.sVendorCode);
                        String strApdus = writeSIMCardJni.GetWriteCardApdu(1, 1, strWriteCardData, strExternData);
                        if (isLog) {
                            LogWriter.getInstance().print("组件返回写卡指令：" + strApdus);
                        }

                        if (strApdus.equals("")) {
                            isAuth = false;
                            this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                            return gson.toJson(resp);
                        } else {
                            String closeFlag = strApdus.substring(strApdus.length() - 1);
                            int closeNum = Integer.parseInt(closeFlag);
                            int indexStage = 2 - closeNum;
                            String strRandCmd = "";
                            String strRand = "";
                            String strCloseCardCmd = "";
                            strApdus = strApdus.substring(0, strApdus.length() - 2);
                            String[] strApdu = strApdus.split(";");
                            int ret = -1;
                            ret = sendApduListBusiness(strApdu);
//                            int ret = true;
//                            int ret = this.sendApduListBusiness(strApdu);
                            if (isLog) {
                                LogWriter.getInstance().print("发卡结果反馈：" + ret);
                            }

                            if (ret != 0) {
                                isAuth = false;
                                this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                return gson.toJson(resp);
                            } else {
                                String respRet;
                                for (int i = 0; i < closeNum; i++) {
                                    respRet = null;
                                    strRandCmd = writeSIMCardJni.CloseCardRandNum();
                                    ret = this.SendCloseCardApdu(strRandCmd, respRet);
                                    if (ret != 0) {
                                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                        return gson.toJson(resp);
                                    }
                                    strRand = respRet;
                                    strCloseCardCmd = writeSIMCardJni.CloseCard(indexStage, respRet);
                                    ret = this.SendCloseCardApdu(strRandCmd, respRet);
                                    if (ret != 0) {
                                        this.packRspInfo(resp, ErrorsEnum.C0004.getCode(), ErrorsEnum.C0004.getMessage());
                                        return gson.toJson(resp);
                                    }
                                    indexStage++;
//                                    ++indexStage;
                                }

                                String ICCSERIAL = this.readCardICCSERIAL(this.sVendorCode);
                                String ICCSERIAL_4 = ICCSERIAL.substring(ICCSERIAL.length() - 4, ICCSERIAL.length());
//                                respRet = ICCSERIAL.substring(ICCSERIAL.length() - 4, ICCSERIAL.length());
//                                String ICCSERIAL_4_ASCII = StringUtil.HEX_2_ASC(respRet);
                                String ICCSERIAL_4_ASCII = StringUtil.HEX_2_ASC(ICCSERIAL_4);
                                StringBuilder sb = new StringBuilder();
                                sb.append(ICCSERIAL.substring(0, ICCSERIAL.length() - 4));
                                sb.append(ICCSERIAL_4_ASCII);
                                ICCSERIAL = sb.toString();
                                resp.setICCSERIAL(ICCSERIAL);
                                resp.setICCID(ICCID);
                                resp.setCIMSI(CIMSI);
                                resp.setGIMSI(GIMSI);
                                resp.setUSIMIMSI(USIMIMSI);
                                if (isLog) {
                                    LogWriter.getInstance().print("组件返回写卡结果参数：ICCSERIAL->" + ICCSERIAL + "ICCID->" + ICCID + "CIMSI->" + CIMSI + "GIMSI->" + GIMSI + "USIMIMSI->" + USIMIMSI);
                                }

                                isAuth = false;
                                this.packRspInfo(resp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
                                return gson.toJson(resp);
                            }
                        }
                    }
                }
            } else {
                isAuth = false;
                this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
                return gson.toJson(resp);
            }
        } else {
            this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
            return gson.toJson(resp);
        }
    }
*/

/*
    public int sendApduListBusiness(String[] apdu) {
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int res = true;
        int iRet = 1;
        if (this.sVendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }

        if (isLog) {
            LogWriter.getInstance().print("发卡器上电结果:" + iRet);
        }

        int res = this.SendWriteCardApdu(apdu);
        if (isLog) {
            LogWriter.getInstance().print("发卡器发送指令结果:" + res);
        }

        if (this.sVendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
        }

        if (isLog) {
            LogWriter.getInstance().print("发卡器下电结果:" + iRet);
        }

        return res;
    }
*/

    public int sendApduListBusiness(String[] apdu) {
        byte[] pData = new byte[128];
//        byte[] pData = new byte['��'];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int res = -1;
        int iRet = 1;
        if (this.sVendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }
        if (isLog.booleanValue()) {
            LogWriter.getInstance().print("发卡器上电结果" + iRet);
        }
        res = SendWriteCardApdu(apdu);
        if (isLog.booleanValue()) {
            LogWriter.getInstance().print("发卡器发送指令结果" + res);
        }
        if (this.sVendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
        }
        if (isLog.booleanValue()) {
            LogWriter.getInstance().print("发卡器下电结果" + iRet);
        }
        return res;
    }


    public String readCardApduBusiness(String vendorCode) {
        if (isLog) {
            LogWriter.getInstance().print("读卡开始:");
        }

        ReadCardResp resp = new ReadCardResp();
        Gson gson = new Gson();
        String retValue = this.readCardICCID(vendorCode);
        if (isLog) {
            LogWriter.getInstance().print("读取卡片序列号结果:" + retValue);
        }

        if (isLog) {
            LogWriter.getInstance().print("读卡结束:");
        }

        if (retValue != null) {
            resp.setICCID(retValue);
            this.packRspInfo(resp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
        } else {
            this.packRspInfo(resp, ErrorsEnum.C0009.getCode(), ErrorsEnum.C0009.getMessage());
        }

        return gson.toJson(resp);
    }

    public String getRandNum() {
        RandNumResp resp = new RandNumResp();
        Gson gson = new Gson();
        String rand = writeSIMCardJni.GetRandNum();
        if (rand == null) {
            this.packRspInfo(resp, ErrorsEnum.C0006.getCode(), ErrorsEnum.C0006.getMessage());
            return gson.toJson(resp);
        } else {
            resp.setRandNum(rand);
            this.packRspInfo(resp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
            return gson.toJson(resp);
        }
    }

    //鉴权
    public String authentication(String reqParam) {
        CommonRspInfo resp = new CommonRspInfo();
        Gson gson = new Gson();
        RandNumReq req = (RandNumReq) gson.fromJson(reqParam, RandNumReq.class);
        if (req == null) {
            this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
            return gson.toJson(resp);
        } else {
            String authCode = req.getAuthCode();
            if (authCode == null) {
                this.packRspInfo(resp, ErrorsEnum.C0001.getCode(), ErrorsEnum.C0001.getMessage());
                return gson.toJson(resp);
            } else {
                int isauth = writeSIMCardJni.Authentication(authCode);
                if (isauth == 1) {
                    isAuth = false;
                    this.packRspInfo(resp, ErrorsEnum.C0007.getCode(), ErrorsEnum.C0007.getMessage());
                    return gson.toJson(resp);
                } else {
                    isAuth = true;
                    this.packRspInfo(resp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
                    return gson.toJson(resp);
                }
            }
        }
    }

    public String readCardICCID(String vendorCode) {
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4000C022FE29000";
        String apdu3 = "00B000000A9000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = -1;
        if (isLog) {
            LogWriter.getInstance().print("读卡上电:");
        }

        if (isLog) {
            if (zrisscimcard_dri != null) {
                LogWriter.getInstance().print("卓睿对象1:" + zrisscimcard_dri.toString());
                LogWriter.getInstance().print("卓睿对象2:" + zrisscimcard_dri.hashCode());
            } else {
                LogWriter.getInstance().print("卓睿对象3:" + zrisscimcard_dri);
            }
        }

        if (isLog) {
            if (cwisscimcard_dri != null) {
                LogWriter.getInstance().print("兆维对象1:" + cwisscimcard_dri.toString());
                LogWriter.getInstance().print("兆维对象2:" + cwisscimcard_dri.hashCode());
            } else {
                LogWriter.getInstance().print("兆维对象3:" + cwisscimcard_dri);
            }
        }

        if (isLog) {
            if (zdexternalCall != null) {
                LogWriter.getInstance().print("中鼎对象1:" + zdexternalCall.toString());
                LogWriter.getInstance().print("中鼎对象2:" + zdexternalCall.hashCode());
            } else {
                LogWriter.getInstance().print("中鼎对象3:" + zdexternalCall);
            }
        }

        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }

        if (isLog) {
            LogWriter.getInstance().print("读卡上电结果:" + iRet);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        if (isLog) {
            LogWriter.getInstance().print("读卡发送APDU:");
        }

        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        retValue = StringUtil.getStr(retValue);
        if (isLog) {
            LogWriter.getInstance().print("ICCID转换后：" + retValue);
        }

        if (isLog) {
            LogWriter.getInstance().print("读卡下电:");
        }

        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
        }

        if (isLog) {
            LogWriter.getInstance().print("读卡下电结果:" + iRet);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    public String readCardICCSERIAL(String vendorCode) {
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4000C022F029000";
        String apdu3 = "00B000000B9000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    public String readCardC_IMSI(String vendorCode) {
//        new ReadCardResp();
        ReadCardResp resp = new ReadCardResp();
//        new Gson();
        Gson gson = new Gson();
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4000C027F259000";
        String apdu3 = "00A4000C026F229000";
        String apdu4 = "00B000000A9000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3, apdu4};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    public String readCardG_IMSI(String vendorCode) {
//        new ReadCardResp();
        ReadCardResp resp = new ReadCardResp();
//        new Gson();
        Gson gson = new Gson();
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4000C027F209000";
        String apdu3 = "00A4000C026F079000";
        String apdu4 = "00B00000099000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3, apdu4};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    public String readCardUSIM_IMSI(String vendorCode) {
//        new ReadCardResp();
        ReadCardResp resp = new ReadCardResp();
//        new Gson();
        Gson gson = new Gson();
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4040010A0000000871002FF86FF0389FFFFFFFF9000";
        String apdu3 = "00A4000C026F079000";
        String apdu4 = "00B00000099000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3, apdu4};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    private String getCardType(String vendorCode) {
//        new ReadCardResp();
        ReadCardResp resp = new ReadCardResp();
//        new Gson();
        Gson gson = new Gson();
        String apdu1 = "00A4000C023F009000";
        String apdu2 = "00A4000C022FFF9000";
        String apdu3 = "00B000000F9000";
        String[] strApdu = new String[]{apdu1, apdu2, apdu3};
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        String retValue = null;
        retValue = this.SendReadCardApdu(strApdu, vendorCode);
        if (retValue != null) {
            retValue = retValue.substring(18, 20);
        }

        if (vendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
        } else if (vendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, vendorCode);
        return retValue;
    }

    private int SendWriteCardApdu(String[] strApdu) {
        int retValue = 0;

        for (int i = 0; i < strApdu.length; i++) {
            String apdu = strApdu[i].substring(2, strApdu[i].length() - 4);
            LogWriter.getInstance().print("APDU[" + i + "] = " + apdu);
            byte[] pInData = new byte[256];
            int pInDataLen = 0;
//            int pInDataLen = false;
            byte[] pOutData = new byte[256];
            int[] iOutDataLen = new int[1];
            byte[] pExeInfor = new byte[64];
            pInData = DataUtils.HexToByteArr(apdu);
            pInDataLen = apdu.length() / 2;
//            int pInDataLen = apdu.length() / 2;
            if (this.sVendorCode.equals(stringZR)) {
                retValue = zrisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
            } else if (this.sVendorCode.equals(stringZW)) {
                retValue = cwisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
            } else if (this.sVendorCode.equals(stringZD)) {
                retValue = zdexternalCall.ApduExchange3(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
                pOutData = zdexternalCall.APDUrealData(pOutData, iOutDataLen);
            }

            String strSW = strApdu[i].substring(strApdu[i].length() - 4, strApdu[i].length());
            String retStr = new String(pOutData);
            retStr = retStr.trim();
            int index = retStr.length();
            String strRetDaSW = retStr.substring(index - 4, index);
            if ((retValue != 0 || !strRetDaSW.equals(strSW)) && !apdu.substring(0, 8).equals("00200001") && (!strSW.toLowerCase().equals("61xx") || !strRetDaSW.startsWith("61"))) {
                if (isLog) {
                    LogWriter.getInstance().print("第" + i + "条指令" + apdu + "指令结果:" + strRetDaSW);
                }

                return -1;
            }
        }

        if (isLog) {
            LogWriter.getInstance().print("指令结果成功！");
        }

        return 0;
    }

    private String SendReadCardApdu(String[] strApdu, String vendorCode) {
        String sZdresult = "";
        int retValue = 0;
        String iccid = null;

        for (int i = 0; i < strApdu.length; i++) {
            String apdu = strApdu[i].substring(0, strApdu[i].length() - 4);
            byte[] pInData = new byte[256];
            int pInDataLen = 0;
//            int pInDataLen = false;
            int[] iOutDataLen = new int[1];
            byte[] pOutData = new byte[256];
            byte[] pExeInfor = new byte[64];
            pInData = DataUtils.HexToByteArr(apdu);
//            int pInDataLen = apdu.length() / 2;
            pInDataLen = apdu.length() / 2;
            if (vendorCode.equals(stringZR)) {
                retValue = zrisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
            } else if (vendorCode.equals(stringZW)) {
                retValue = cwisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
            } else if (vendorCode.equals(stringZD)) {
                retValue = zdexternalCall.ApduExchange3(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
                pOutData = zdexternalCall.APDUrealData(pOutData, iOutDataLen);
            }

            this.Display_Result(retValue, pExeInfor, vendorCode);
            String strSW = strApdu[i].substring(strApdu[i].length() - 4, strApdu[i].length());
            String retStr = new String(pOutData);
            retStr = retStr.trim();
            int index = retStr.length();
            String strRetDaSW = retStr.substring(index - 4, index);
            if (isLog) {
                LogWriter.getInstance().print("发卡器发送指令结果: 第" + i + "次，结果：" + strRetDaSW);
            }

            String sRet;
            if (retValue != 0 || !strRetDaSW.equals(strSW)) {
                sRet = Integer.toString(retValue);
                byte[] bErrorInfo = new byte[64];
                if (vendorCode.equals(stringZR)) {
                    zrisscimcard_dri.GetLastErrInfo(retValue, bErrorInfo);
                } else if (vendorCode.equals(stringZW)) {
                    cwisscimcard_dri.GetLastErrInfo(retValue, bErrorInfo);
                } else if (vendorCode.equals(stringZD)) {
                    bErrorInfo = zdexternalCall.errordes(sRet).getBytes();
                }

                String sError = new String(bErrorInfo);
                return iccid;
            }

            if (i >= strApdu.length - 1) {
                sRet = new String(pOutData);
                int indexs = sRet.indexOf("9000");
                iccid = sRet.substring(0, indexs);
                if (isLog) {
                    LogWriter.getInstance().print("卡片ICCID:" + iccid);
                }

                return iccid;
            }
        }

        return iccid;
    }

    public int SendCloseCardApdu(String strRandCmd, String reqRet) {
        byte[] pData = new byte[128];
        int[] iDataLen = new int[1];
        byte[] pExeInfor = new byte[64];
        int iRet = 1;
        if (this.sVendorCode.equals(stringZR)) {
            iRet = zrisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            iRet = cwisscimcard_dri.Sim_PowerOn(pData, iDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            iRet = zdexternalCall.cpucardpowerup(pExeInfor);
        }

        this.Display_Result(iRet, pExeInfor, this.sVendorCode);
        byte[] pInData = new byte[256];
        int pInDataLen = 0;
//        int pInDataLen = false;
        int[] iOutDataLen = new int[1];
        byte[] pOutData = new byte[256];
        pInData = DataUtils.HexToByteArr(strRandCmd);
        pInDataLen = strRandCmd.length() / 2;
//        int pInDataLen = strRandCmd.length() / 2;
        int ret = -1;
        if (this.sVendorCode.equals(stringZR)) {
            ret = zrisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZW)) {
            ret = cwisscimcard_dri.Sim_ApduExchange(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
        } else if (this.sVendorCode.equals(stringZD)) {
            ret = zdexternalCall.ApduExchange3(pInData, pInDataLen, pOutData, iOutDataLen, pExeInfor);
            pOutData = zdexternalCall.APDUrealData(pOutData, iOutDataLen);
        }

        String retStr = new String(pOutData);
        retStr = retStr.trim();
        int index = retStr.length();
        String strRetDaSW = retStr.substring(index - 4, index);
        if (ret == 0 && "9000".equals(strRetDaSW)) {
//            retStr.substring(0, index - 4);
            reqRet = retStr.substring(0, index - 4);
            if (this.sVendorCode.equals(stringZR)) {
                iRet = zrisscimcard_dri.Sim_PowerOff(pExeInfor);
            } else if (this.sVendorCode.equals(stringZW)) {
                iRet = cwisscimcard_dri.Sim_PowerOff(pExeInfor);
            } else if (this.sVendorCode.equals(stringZD)) {
                iRet = zdexternalCall.cpucardpowerdown(pExeInfor);
            }

            this.Display_Result(iRet, pExeInfor, this.sVendorCode);
            return 0;
        } else {
            return -1;
        }
    }

    public void Display_Result(int iRetCode, byte[] bExeInfo, String vendorCode) {
        String sRet;
        if (iRetCode == 0) {
            sRet = Integer.toString(iRetCode);
            String sExeInfo;
            if (vendorCode.equals(stringZD)) {
                sExeInfo = zdexternalCall.statedes(zdexternalCall.resultcode(bExeInfo));
                if (isLog) {
                    LogWriter.getInstance().print("返回值:" + sRet + "执行结果:" + sExeInfo);
                }
            } else {
                sExeInfo = new String(bExeInfo);
                if (isLog) {
                    LogWriter.getInstance().print("返回值:" + sRet + ";执行结果: " + sExeInfo);
                }
            }
        } else {
            sRet = Integer.toString(iRetCode);
            byte[] bErrorInfo = new byte[64];
            String st;
            if (vendorCode.equals(stringZR)) {
                zrisscimcard_dri.GetLastErrInfo(iRetCode, bErrorInfo);
                st = new String(bErrorInfo);
                if (isLog) {
                    LogWriter.getInstance().print("返回值:" + sRet + " 错误描述：" + st);
                }
            } else if (vendorCode.equals(stringZW)) {
                cwisscimcard_dri.GetLastErrInfo(iRetCode, bErrorInfo);
                st = new String(bErrorInfo);
                if (isLog) {
                    LogWriter.getInstance().print("返回值:" + sRet + " 错误描述：" + st);
                }
            } else if (vendorCode.equals(stringZD)) {
                if (iRetCode == 2) {
                    st = zdexternalCall.errordes(zdexternalCall.resultcode(bExeInfo));
                    if (isLog) {
                        LogWriter.getInstance().print("返回值:" + sRet + "执行结果：错误描述：" + st);
                    }
                } else if (isLog) {
                    LogWriter.getInstance().print("返回值:" + sRet + "错误描述：执行完命令，返回非零");
                }
            }
        }

    }

    static {
        System.loadLibrary("WriteSIMCard-jni");
        if (isLog) {
            try {
                LogWriter.getInstance();
                LogWriter.open();
            } catch (IOException var1) {
                var1.printStackTrace();
            }

            LogWriter.getInstance().print("打开日志文件：");
        }

    }
}
