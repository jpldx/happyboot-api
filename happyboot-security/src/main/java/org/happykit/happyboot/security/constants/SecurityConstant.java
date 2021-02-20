package org.happykit.happyboot.security.constants;

/**
 * Security相关常量
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
public class SecurityConstant {

    /**
     * 交互token前缀key
     */
    public static final String TOKEN_PRE = "TOKEN_PRE:";

    /**
     * 用户token前缀key 单点登录使用
     */
    public static final String USER_TOKEN = "USER_TOKEN:";

    /**
     * 权限参数头
     */
    public static final String AUTHORITIES = "authorities";

    /**
     * 登录错误限制前缀
     */
    public static final String LOGIN_FAILED_LIMIT_TIMES = "login_failed_limit_times:";

    /**
     *
     */
    public static final String LOGIN_FAILED_LIMIT_FLAG = "login_failed_limit_flag:";


}
