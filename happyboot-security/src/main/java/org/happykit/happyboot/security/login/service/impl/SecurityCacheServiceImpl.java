package org.happykit.happyboot.security.login.service.impl;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.happykit.happyboot.security.login.service.SecurityCacheService;
import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/27
 */
@Service
public class SecurityCacheServiceImpl implements SecurityCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void setUserDetails(SecurityUserDetails userDetails) {
        redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + userDetails.getToken(),
                new Gson().toJson(userDetails),
                tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
    }

    @Override
    public SecurityUserDetails getUserDetails(String token) {
        String value = (String) redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + token);
        if (value == null) {
            return null;
        }
        return new Gson().fromJson(value, SecurityUserDetails.class);
    }

    @Override
    public void removeUserDetails(String token) {
        redisTemplate.delete(SecurityConstant.USER_TOKEN + token);
    }

    @Override
    public void setTokenToBlackList(String token) {
        redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN_BLACKLIST + token,
                token, tokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
    }

    @Override
    public Set<String> getTokenBlackList() {
        return redisTemplate.keys(SecurityConstant.USER_TOKEN_BLACKLIST + "*");
    }

    @Override
    public boolean isTokenInBlackList(String token) {
        Set<String> blacklist = this.getTokenBlackList();
        return blacklist.contains(SecurityConstant.USER_TOKEN_BLACKLIST + token);
    }
}
