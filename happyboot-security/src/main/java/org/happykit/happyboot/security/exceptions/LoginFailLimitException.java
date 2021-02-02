package org.happykit.happyboot.security.exceptions;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 多次登录失败异常
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */

public class LoginFailLimitException extends InternalAuthenticationServiceException {

    public LoginFailLimitException(String message) {
        super(message);
    }

    public LoginFailLimitException(String message, Throwable cause) {
        super(message, cause);
    }

}
