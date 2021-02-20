package org.happykit.happyboot.security.login;

import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.happykit.happyboot.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 登录凭据失败监听
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/7/23
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
//        Authentication authentication = event.getAuthentication();
//        String username = authentication.getName();
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String clientId = request.getHeader(tokenProperties.getClientId());

        // 缓存计数
        String key = SecurityConstant.LOGIN_FAILED_LIMIT_TIMES + clientId;
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, tokenProperties.getLoginFailedLimitRecoverTime(), TimeUnit.MINUTES);
    }
}
