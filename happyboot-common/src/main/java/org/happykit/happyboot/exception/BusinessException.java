package org.happykit.happyboot.exception;

/**
 * 业务异常
 *
 * @author shaoqiang
 * @version 1.0 2020/5/11
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
