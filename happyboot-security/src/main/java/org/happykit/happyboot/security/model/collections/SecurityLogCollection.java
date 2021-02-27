package org.happykit.happyboot.security.model.collections;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户安全日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
@Data
@Accessors(chain = true)
@Document(collection = "security_logs")
public class SecurityLogCollection {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 操作类型
     * {@link org.happykit.happyboot.security.constants.SecurityConstant.SecurityOperationEnum}
     */
    private String operationType;
    /**
     * 操作平台（PC/APP）
     * ${@link org.happykit.happyboot.enums.AppPlatformEnum}
     */
    private String operationPlatform;
    /**
     * 操作时间
     */
    private String operationTime;
    /**
     * 令牌
     */
    private String token;
    /**
     * 令牌过期时间
     */
    private Date tokenExpireTime;
    /**
     * ip
     */
    private String ip;
    /**
     * ip所在地址
     */
    private String ipAddress;
    /**
     * 客户端UA信息
     */
    private String ua;
}
