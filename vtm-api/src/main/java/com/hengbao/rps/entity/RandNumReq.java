package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 随机数请求
 */

public class RandNumReq {
    private String authCode;

    public RandNumReq() {
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
