package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 写卡参数
 */

public class WriteCardReq {
    private String cardData;
    private String vendorCode;
    private String Account;
    private String appkey;
    private String pin;

    public WriteCardReq() {
    }

    public String getCardData() {
        return this.cardData;
    }

    public void setCardData(String cardData) {
        this.cardData = cardData;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getAccount() {
        return this.Account;
    }

    public void setAccount(String account) {
        this.Account = account;
    }

    public String getAppkey() {
        return this.appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
