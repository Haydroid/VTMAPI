package com.hengbao.rps.exception;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 异常集合
 */

public enum ErrorsEnum {
    S0000("S0000", "", "系统异常"),
    S0001("S0001", "", "接口通讯错误"),
    C0000("C0000", "", "操作成功"),
    C0001("C0001", "", "业务处理异常"),
    C0002("C0002", "", "报文格式错误"),
    C0003("C0003", "", "上送报文对象为空"),
    C0004("C0004", "", "报文参数错误"),
    C0005("C0005", "", "支付信息已存在"),
    C0006("C0006", "", "数据库保存数据错误"),
    C0007("C0007", "", "组件鉴权失败"),
    C0008("C0008", "", "打开密码键盘串口失败"),
    C0009("C0009", "", "激活密钥失败"),
    C0010("C0010", "", "解密报文失败"),
    C0011("C0011", "", "获取组件随机数失败");

    private String code;
    private String outCode;
    private String message;

    private ErrorsEnum(String code, String outCode, String message) {
        this.code = code;
        this.outCode = outCode;
        this.message = message;
    }

    public static ErrorsEnum getErrorsEnum(String code) {
        ErrorsEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ErrorsEnum ece = var1[var3];
            if (ece.getCode().equals(code)) {
                return ece;
            }
        }

        return null;
    }

    public static ErrorsEnum getErrorsEnumByOutCode(String outCode) {
        ErrorsEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ErrorsEnum ece = var1[var3];
            if (ece.getOutCode().equals(outCode)) {
                return ece;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOutCode() {
        return this.outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }
}
