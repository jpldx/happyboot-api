package org.happykit.happyboot.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * 客户端登录失败限制异常
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/20
 */
public class LoginFailedLimitException extends AuthenticationException {


    public LoginFailedLimitException(String msg) {
        super(msg);
    }

    public LoginFailedLimitException(String msg, Throwable t) {
        super(msg, t);
    }

}
