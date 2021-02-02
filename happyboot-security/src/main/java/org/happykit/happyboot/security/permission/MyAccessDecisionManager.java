package org.happykit.happyboot.security.permission;//package com.litong.ltitframework.boot.core.security.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义决策管理器
 * 功能说明：登陆用户访问权限判断过滤器
 *
 * @author shaoqiang
 * @version 1.0 2020/3/10
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    private static final Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    /**
     * 决策方法： 如果方法执行完毕没有抛出异常,则说明可以放行, 否则抛出异常
     *
     * @param authentication   认证过的票据Authentication，确定了谁正在访问资源
     * @param object           被访问的资源object
     * @param configAttributes 访问资源要求的权限配置ConfigAttributeDefinition
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 访问资源需要配置的权限
        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }

        // 所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterable = configAttributes.iterator();
        while (iterable.hasNext()) {
            ConfigAttribute c = iterable.next();
            // 访问所请求资源所需要的权限
            String needPerm = c.getAttribute();
            // 用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needPerm.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}