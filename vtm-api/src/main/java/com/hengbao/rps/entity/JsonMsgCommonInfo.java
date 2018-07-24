package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : JsonMsgCommonInfo
 */

public class JsonMsgCommonInfo {
    protected String strId;
    protected String strVersion;
    protected String strName;
    protected String strOrder;

    public JsonMsgCommonInfo() {
    }

    public String getStrId() {
        return this.strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrVersion() {
        return this.strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }

    public String getStrName() {
        return this.strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrOrder() {
        return this.strOrder;
    }

    public void setStrOrder(String strOrder) {
        this.strOrder = strOrder;
    }
}
