package org.happykit.happyboot.base;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
public enum RCode {

    /**
     * 成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 失败
     */
    FAILED(-1, "操作失败");

    int code;
    String msg;

    RCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
