package org.happykit.happyboot.security.login.service;

import org.happykit.happyboot.security.model.SecurityUserDetails;

import java.util.Set;

/**
 * 安全缓存
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
public interface SecurityCacheService {

    /**
     * 缓存用户认证信息
     *
     * @param userDetails
     */
    void setUserDetails(SecurityUserDetails userDetails);

    /**
     * 获取用户认证信息
     *
     * @param token
     * @return
     */
    SecurityUserDetails getUserDetails(String token);

    /**
     * 删除用户认证信息缓存
     *
     * @param token
     */
    void removeUserDetails(String token);

    /**
     * 将token缓存至黑名单
     * 必须设置过期时间慢于jwt过期时间
     *
     * @param token
     */
    void setTokenToBlackList(String token);

    /**
     * 获取token黑名单列表
     *
     * @return
     */
    Set<String> getTokenBlackList();

    /**
     * 判断token是否在黑名单内
     *
     * @param token
     * @return
     */
    boolean isTokenInBlackList(String token);
}
