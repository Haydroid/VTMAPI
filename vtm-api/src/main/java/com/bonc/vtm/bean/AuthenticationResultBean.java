package com.bonc.vtm.bean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : AuthenticationResultBean
 */

public class AuthenticationResultBean {

    /**
     * {"retCode":"C0000","retMsg":"操作成功"}
     * C0000	返回结果成功
     * C0001	业务处理异常
     * C0007	组件鉴权失败
     */

    private String retCode;
    private String retMsg;

    public AuthenticationResultBean() {
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
        return "AuthenticationResultBean{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }
}
