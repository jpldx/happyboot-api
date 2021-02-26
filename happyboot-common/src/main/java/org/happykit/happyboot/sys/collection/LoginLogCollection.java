package org.happykit.happyboot.sys.collection;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户登录日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@Data
@Accessors(chain = true)
@Document(collection = "login_logs")
public class LoginLogCollection {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 登录平台（pc/app）
     */
    private String platform;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录时间
     */
    private String loginTime;
    /**
     * 登录令牌
     */
    private String token;
    /**
     * 令牌过期时间
     */
    private Date tokenExpireTime;
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 登录ip所在地址
     */
    private String ipAddress;
    /**
     * 客户端UA信息
     */
    private String ua;
}
