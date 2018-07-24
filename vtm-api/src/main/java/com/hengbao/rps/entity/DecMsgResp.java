package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 实体类
 */

public class DecMsgResp extends CommonRspInfo {
    private String decData;

    public DecMsgResp() {
    }

    public String getDecData() {
        return this.decData;
    }

    public void setDecData(String decData) {
        this.decData = decData;
    }
}
