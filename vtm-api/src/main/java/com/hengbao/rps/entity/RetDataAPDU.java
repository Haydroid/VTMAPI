package com.hengbao.rps.entity;

/**
 * @Author : YangWei
 * @Date : 2018/6/28
 * @Description : APDU指令执行信息
 */

public class RetDataAPDU {
    public int retDataLen;
    //    public byte[] retData;
    public byte[] retData = new byte[7000];
    public int SW;
    public int SW2;
    public int timer_ms;
    public long times_total;

    public RetDataAPDU() {
//        this.retData = new byte[7000];
    }
}
