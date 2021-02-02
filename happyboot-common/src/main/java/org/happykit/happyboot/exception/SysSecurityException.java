package org.happykit.happyboot.exception;

/**
 * 系统安全异常
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/8
 */
public class SysSecurityException extends RuntimeException {
    public SysSecurityException(String message) {
        super(message);
    }

    public SysSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysSecurityException(Throwable cause) {
        super(cause);
    }
}
