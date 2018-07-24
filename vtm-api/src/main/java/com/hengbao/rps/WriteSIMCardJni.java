package com.hengbao.rps;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 写卡接口
 */

public class WriteSIMCardJni {
    public WriteSIMCardJni() {
    }

    public native String GetDllVersion();

    public native String GetRandNum();

    public native int Authentication(String var1);

    public native String GetWriteCardApdu(int var1, int var2, String var3, String var4);

    public native String CloseCardRandNum();

    public native String CloseCard(int var1, String var2);
}
