package com.hengbao.rps.Auth.impl;

import com.google.gson.Gson;
import com.hengbao.rps.Auth.AuthInterface;
import com.hengbao.rps.BaseBusiness;
import com.hengbao.rps.WriteSIMCardJni;
import com.hengbao.rps.entity.AuthResp;
import com.hengbao.rps.entity.CommonRspInfo;
import com.hengbao.rps.exception.ErrorsEnum;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 写卡鉴权
 */

public class AuthImpl extends BaseBusiness implements AuthInterface {
    public static WriteSIMCardJni writeSIMCardJni = new WriteSIMCardJni();

    public AuthImpl() {
    }

    public String getRandNum() {
        AuthResp authResp = new AuthResp();
        Gson gson = new Gson();
        String rand = writeSIMCardJni.GetRandNum();
        if (rand != null && !"".equals(rand)) {
            authResp.setRandNum(rand);
            this.packRspInfo(authResp, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
            String res = gson.toJson(authResp);
            return res;
        } else {
            this.packRspInfo(authResp, ErrorsEnum.C0011.getCode(), ErrorsEnum.C0011.getMessage());
            return gson.toJson(authResp);
        }
    }

    public String authentication(String authCode) {
        CommonRspInfo commonRspInfo = new CommonRspInfo();
        Gson gson = new Gson();
        int isAuth = writeSIMCardJni.Authentication(authCode);
        if (isAuth == 1) {
            this.packRspInfo(commonRspInfo, ErrorsEnum.C0007.getCode(), ErrorsEnum.C0007.getMessage());
            return gson.toJson(commonRspInfo);
        } else {
            this.packRspInfo(commonRspInfo, ErrorsEnum.C0000.getCode(), ErrorsEnum.C0000.getMessage());
            String res = gson.toJson(commonRspInfo);
            return res;
        }
    }

    static {
        System.loadLibrary("WriteSIMCard-jni");
    }
}
