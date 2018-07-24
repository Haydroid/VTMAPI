package com.bonc.vtm.bean;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : RandomBean
 */

public class RandomBean {

    /**
     * {"randNum":"1C0D9DF1E787A497","retCode":"C0000","retMsg":"操作成功"}
     * <p>
     * C0000	返回结果成功
     * C0001	业务处理异常
     * C0006	获取随机数失败
     */


    private String randNum;
    private String retCode;
    private String retMsg;

    public RandomBean() {
    }

    public RandomBean(String randNum, String retCode, String retMsg) {
        this.randNum = randNum;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "RandomBean{" +
                "randNum='" + randNum + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }
}
