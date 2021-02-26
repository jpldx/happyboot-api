package org.happykit.happyboot.security.constants;

/**
 * Security 相关常量
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
public class SecurityConstant {

    /**
     * token
     */
    public static final String TOKEN_PRE = "TOKEN_PRE:";

    /**
     * token（单点登录使用）
     */
    public static final String USER_TOKEN = "USER_TOKEN:";

    /**
     * 登录失败限制
     */
    public static final String LOGIN_FAILED_LIMIT = "login_failed_limit:";

    /**
     * 匿名用户（未认证用户）
     */
    public static final String ANONYMOUS_USER = "anonymous";

    /**
     * 账号类型（0/主账号 1/子账号）
     */
    public static final String USER_TYPE_0 = "0";
    public static final String USER_TYPE_1 = "1";
}
