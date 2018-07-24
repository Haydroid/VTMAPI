package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : PersonalData
 */

public class PersonalData extends JsonMsgCommonInfo {
    private String strIndex;
    private String strNumOfPhone;
    private String strWriteCardData;
    private String strExternData;

    public PersonalData() {
    }

    public String getStrIndex() {
        return this.strIndex;
    }

    public void setStrIndex(String strIndex) {
        this.strIndex = strIndex;
    }

    public String getStrNumOfPhone() {
        return this.strNumOfPhone;
    }

    public void setStrNumOfPhone(String strNumOfPhone) {
        this.strNumOfPhone = strNumOfPhone;
    }

    public String getStrWriteCardData() {
        return this.strWriteCardData;
    }

    public void setStrWriteCardData(String strWriteCardData) {
        this.strWriteCardData = strWriteCardData;
    }

    public String getStrExternData() {
        return this.strExternData;
    }

    public void setStrExternData(String strExternData) {
        this.strExternData = strExternData;
    }
}
