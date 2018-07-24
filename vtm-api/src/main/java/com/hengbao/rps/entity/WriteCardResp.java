package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 写卡结果信息
 */

public class WriteCardResp extends CommonRspInfo {
    private String ICCID;
    private String ICCSERIAL;
    private String CIMSI;
    private String GIMSI;
    private String USIMIMSI;

    public WriteCardResp() {
    }

    public String getICCID() {
        return this.ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getICCSERIAL() {
        return this.ICCSERIAL;
    }

    public void setICCSERIAL(String ICCSERIAL) {
        this.ICCSERIAL = ICCSERIAL;
    }

    public String getCIMSI() {
        return this.CIMSI;
    }

    public void setCIMSI(String CIMSI) {
        this.CIMSI = CIMSI;
    }

    public String getGIMSI() {
        return this.GIMSI;
    }

    public void setGIMSI(String GIMSI) {
        this.GIMSI = GIMSI;
    }

    public String getUSIMIMSI() {
        return this.USIMIMSI;
    }

    public void setUSIMIMSI(String USIMIMSI) {
        this.USIMIMSI = USIMIMSI;
    }
}
