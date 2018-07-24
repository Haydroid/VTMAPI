package com.bonc.vtm.bean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : WriteCardParamsBean
 */

public class WriteCardParamsBean {

    private String cardData;//卡数据密文
    private String vendorCode;//设备型号编码 ZW01 兆维 ZD01 中鼎 ZR01 卓睿
    private String Account;//meid
    private String appkey;//Mdm提供的授权KEYMdm提供的授权KEY，应用app对应账号的密码
    private String pin;//Pin密码

    public String getCardData() {
        return cardData;
    }

    public void setCardData(String cardData) {
        this.cardData = cardData;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "WriteCardParamsBean{" +
                "cardData='" + cardData + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", Account='" + Account + '\'' +
                ", appkey='" + appkey + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
