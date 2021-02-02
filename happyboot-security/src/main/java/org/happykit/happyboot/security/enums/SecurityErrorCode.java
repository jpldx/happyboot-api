package org.happykit.happyboot.security.enums;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/18
 */
public enum SecurityErrorCode implements IErrorCode {
    UNAUTHORIZED(401L, "未授权"),
    FORBIDDEN(403L, "禁止访问"),
    SERVICE_UNAVAILABLE(503L, "服务不可用");
    private final long code;
    private final String msg;

    private SecurityErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
