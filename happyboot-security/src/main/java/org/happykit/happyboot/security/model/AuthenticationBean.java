package org.happykit.happyboot.security.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录对象
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
@Getter
@Setter
public class AuthenticationBean {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
