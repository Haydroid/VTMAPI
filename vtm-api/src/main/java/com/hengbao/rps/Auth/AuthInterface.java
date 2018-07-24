package com.hengbao.rps.Auth;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : 写卡鉴权接口
 */

public abstract interface AuthInterface {
    public abstract String getRandNum();

    public abstract String authentication(String paramString);
}


/*
public interface AuthInterface {
    String getRandNum();

    String authentication(String var1);
}
*/
