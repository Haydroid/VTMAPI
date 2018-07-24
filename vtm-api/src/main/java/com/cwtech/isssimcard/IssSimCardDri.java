package com.cwtech.isssimcard;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 驰卡-硬件驱动接口
 */

public class IssSimCardDri {
    public IssSimCardDri() {
    }

    public native int Sim_OpenDevice(String paramString, long paramLong);

    public native int Sim_CloseDevice();

    public native int Sim_GetVersion(byte[] var1, int[] var2, byte[] var3);

    public native int Sim_GetDevStatus(byte[] var1, int[] var2, byte[] var3);

    public native int Sim_GetCardBoxStatus(byte[] var1, int[] var2, byte[] var3);

    public native int GetLastErrInfo(int paramInt, byte[] paramArrayOfByte);

    public native int Sim_ResetDevice(int paramInt, byte[] paramArrayOfByte);

    public native int Sim_Dispense(int paramInt, byte[] paramArrayOfByte);

    public native int Sim_Eject(byte[] paramArrayOfByte);

    public native int Sim_Capture(int paramInt, byte[] paramArrayOfByte);

    public native int Sim_ReIntake(int paramInt, byte[] paramArrayOfByte);

    public native int Sim_PowerOn(byte[] var1, int[] var2, byte[] var3);

    public native int Sim_PowerOff(byte[] paramArrayOfByte);

    public native int Sim_ApduExchange(byte[] var1, int var2, byte[] var3, int[] var4, byte[] var5);

    public native int Sim_EnableInput(byte[] paramArrayOfByte);

    public native int Sim_DisableInsert(byte[] paramArrayOfByte);

    public native int Sim_ReadTrack(byte var1, byte[] var2, int[] var3, byte[] var4);

    public native int Sim_ReadSingleTrack(byte var1, byte[] var2, int[] var3, byte[] var4);

    public native int Sim_ReadICCID(byte[] var1, int[] var2, byte[] var3);

    public native int Sim_SetLight(int paramInt);

    static {
        System.loadLibrary("CWIssSimCard-lib");
    }
}
