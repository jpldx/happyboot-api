package org.happykit.happyboot.exception;

/**
 * 系统异常类
 *
 * @author chen.xudong
 * @date 2020/6/16
 */
public class SysException extends RuntimeException {
    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
