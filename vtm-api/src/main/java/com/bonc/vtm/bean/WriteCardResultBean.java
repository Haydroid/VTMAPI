package com.bonc.vtm.bean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : WriteCardResultBean
 */

public class WriteCardResultBean {

    /**
     * {
     * "CIMSI": "460036210992350",
     * "GIMSI": "204046504746703",
     * "ICCID": "8986 03157 60107 141426",
     * "ICCSERIAL": "0117000319004060061B",
     * "USIMIMSI": "460110014832274",
     * "retCode": "C0000",
     * "retMsg": "操作成功"
     * }
     *
     * C0000	返回结果成功
     * C0001	业务处理异常
     * C0003	报文解密失败
     * C0004	写卡数据解密失败
     * C0005	获取设备厂商失败
     */


    private String CIMSI;//C网IMSI
    private String GIMSI;//G网IMSI
    private String ICCID;//卡序列号
    private String ICCSERIAL;//空卡序列号
    private String USIMIMSI;//4G IMSI
    private String retCode;//结果代码
    private String retMsg;//结果描述

    public WriteCardResultBean() {
    }

    public WriteCardResultBean(String CIMSI, String GIMSI, String ICCID, String ICCSERIAL, String USIMIMSI, String retCode, String retMsg) {
        this.CIMSI = CIMSI;
        this.GIMSI = GIMSI;
        this.ICCID = ICCID;
        this.ICCSERIAL = ICCSERIAL;
        this.USIMIMSI = USIMIMSI;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getCIMSI() {
        return CIMSI;
    }

    public void setCIMSI(String CIMSI) {
        this.CIMSI = CIMSI;
    }

    public String getGIMSI() {
        return GIMSI;
    }

    public void setGIMSI(String GIMSI) {
        this.GIMSI = GIMSI;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getICCSERIAL() {
        return ICCSERIAL;
    }

    public void setICCSERIAL(String ICCSERIAL) {
        this.ICCSERIAL = ICCSERIAL;
    }

    public String getUSIMIMSI() {
        return USIMIMSI;
    }

    public void setUSIMIMSI(String USIMIMSI) {
        this.USIMIMSI = USIMIMSI;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "WriteCardResultBean{" +
                "CIMSI='" + CIMSI + '\'' +
                ", GIMSI='" + GIMSI + '\'' +
                ", ICCID='" + ICCID + '\'' +
                ", ICCSERIAL='" + ICCSERIAL + '\'' +
                ", USIMIMSI='" + USIMIMSI + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }
}
