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
    public static final String USER_TOKEN = "USER_TOKEN:";

    /**
     * token 黑名单
     */
    public static final String USER_TOKEN_BLACKLIST = "USER_TOKEN_BLACKLIST:";

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

    /**
     * 账号状态（1/启用 0/禁用）
     */
    public static final int ENABLE = 1;
    public static final int DISABLE = 0;


    /**
     * 安全操作枚举
     */
    public enum SecurityOperationEnum {
        /**
         * 登录
         */
        LOGIN,
        /**
         * 登出
         */
        LOGOUT,
        /**
         * 修改密码
         */
        CHANGE_PASSWORD
    }
}
