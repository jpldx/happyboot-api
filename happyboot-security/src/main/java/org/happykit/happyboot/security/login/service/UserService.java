package org.happykit.happyboot.security.login.service;

import org.happykit.happyboot.security.model.SecurityUserDetails;

/**
 * 用户服务接口
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
public interface UserService {
    /**
     * 登录成功
     *
     * @param userDetails
     * @param token
     * @param ip
     * @return
     */
    Object loginSuccess(SecurityUserDetails userDetails, String token, String ip);
}
