package com.bonc.vtm.bean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : ReadCardResultBean
 */

public class ReadCardResultBean {

    /**
     * {"ICCID":"89860315760107141426","retCode":"C0000","retMsg":"操作成功"}
     *
     * C0000	返回结果成功
     * C0001	业务处理异常
     * C0009	获取ICCID失败
     */


    private String ICCID;//卡片的ICCID
    private String retCode;//结果代码
    private String retMsg;//结果描述

    public ReadCardResultBean() {
    }

    public ReadCardResultBean(String ICCID, String retCode, String retMsg) {
        this.ICCID = ICCID;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
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
        return "ReadCardResultBean{" +
                "ICCID='" + ICCID + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }
}
