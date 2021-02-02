package org.happykit.happyboot.security.login;

import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 用户登录失败监听器事件
 *
 * @author shaoqiang
 * @version 1.0
 * @description TODO
 * @date 2020/7/23 11:29
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        loginFailed(username);
    }

    public void loginFailed(String username) {
        // 连续登录错误次数存储标识
        String key = SecurityConstant.LOGIN_TIME_LIMIT + username;
        // 连续登录错误
        String flagKey = SecurityConstant.LOGIN_FAIL_FLAG + username;
        // 从redis获取错误登录次数
        String value = redisTemplate.opsForValue().get(key);
        if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
            value = "0";
        }
        // 累加存入redis
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), tokenProperties.getLoginAfterTime(), TimeUnit.MINUTES);

        // 错误次数超过限定次数,保存用户错限制登录误标识
        if (loginFailTime >= tokenProperties.getLoginTimeLimit()) {
            // 将连续错误多次的用户存入redis，设置过期时间
            redisTemplate.opsForValue().set(flagKey, "fail", tokenProperties.getLoginAfterTime(), TimeUnit.MINUTES);
        }
        int restLoginTime = tokenProperties.getLoginTimeLimit() - loginFailTime;

    }

}
