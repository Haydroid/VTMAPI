package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 获取随机数返回信息
 */

public class RandNumResp extends CommonRspInfo {
    private String randNum;

    public RandNumResp() {
    }

    public String getRandNum() {
        return this.randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }
}
