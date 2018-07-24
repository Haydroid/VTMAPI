package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 随机数实体类
 */

public class AuthResp extends CommonRspInfo {
    private String randNum;

    public AuthResp() {
    }

    public String getRandNum() {
        return this.randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }
}
