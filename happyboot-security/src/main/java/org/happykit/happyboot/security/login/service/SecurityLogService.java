package org.happykit.happyboot.security.login.service;

import org.happykit.happyboot.enums.AppPlatformEnum;
import org.happykit.happyboot.security.constants.SecurityConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全日志
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/3/2
 */
public interface SecurityLogService {
    /**
     * 保存安全日志
     *
     * @param request           请求对象
     * @param userId            用户id
     * @param username          用户名
     * @param securityOperation 操作类型
     * @param appPlatform       操作平台
     * @param token             令牌
     */
    void saveSecurityLog(HttpServletRequest request,
                         String userId,
                         String username,
                         SecurityConstant.SecurityOperationEnum securityOperation,
                         AppPlatformEnum appPlatform,
                         String token);
}
