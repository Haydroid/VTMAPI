package com.hengbao.rps.entity;

import java.io.Serializable;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 指令执行返回信息实体类
 */

public class CommonRspInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String retCode;
    private String retMsg;

    public CommonRspInfo() {
    }

    public String getRetCode() {
        return this.retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
