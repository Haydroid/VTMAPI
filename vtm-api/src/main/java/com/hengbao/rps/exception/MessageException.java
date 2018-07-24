package com.hengbao.rps.exception;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 异常信息
 */

public class MessageException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMsg;

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MessageException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public MessageException(Exception e, int level, String errorCode, String errorMsg) {
        Object eLevel = e;

        try {
            for (int i = 0; i < level; i++) {
                eLevel = ((Throwable) eLevel).getCause();
            }
        } catch (Exception var7) {
            eLevel = e;
        }

        if (eLevel instanceof MessageException) {
            this.errorCode = ((MessageException) eLevel).getErrorCode();
            this.errorMsg = ((MessageException) eLevel).getErrorMsg();
        } else {
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

    }

    public String toString() {
        return " MessageException [ errorCode : " + this.errorCode + ";  errorMsg : " + this.errorMsg + "; ] ";
    }
}
