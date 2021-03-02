package org.happykit.happyboot.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一返回对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@Data
@Accessors(chain = true)
public class R<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     * {@link RCode}
     */
    private int code;
    /**
     * 返回描述
     */
    private String msg;
    /**
     * 返回结果
     */
    private T data;

    public R() {
    }

    public R(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static R ok() {
        return build(null, RCode.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return build(data, RCode.SUCCESS);
    }

    public static <T> R<T> ok(T data, String msg) {
        return build(data, RCode.SUCCESS.getCode(), msg);
    }

    public static R failed() {
        return build(null, RCode.FAILED);
    }

    public static R failed(String msg) {
        return build(null, RCode.FAILED.getCode(), msg);
    }

    private static <T> R<T> build(T data, RCode rCode) {
        return build(data, rCode.getCode(), rCode.getMsg());
    }

    private static <T> R<T> build(T data, int code, String msg) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
