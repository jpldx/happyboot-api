package org.happykit.happyboot.security.permission;//package com.litong.ltitframework.boot.core.security.permission;


import org.happykit.happyboot.security.properties.IgnoredUrlsProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述: 访问资源--授权管理</br>
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 提从数据库提取权限和资源，装配到HashMap中，供Spring Security使用，用于权限校验。
 * 自定义SecurityMetadataSource，实现从数据库加载ConfigAttribute
 * 当访问一个url时返回这个url所需要的访问权限
 *
 * @author shaoqiang
 * @version 1.0 2020/3/10
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);

    /**
     * 存储所有资源与权限
     */
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Resource
    private PermissionService permissionService;

    /**
     * 加载权限表中所有操作请求权限
     */
    public void loadResourceDefine() {
        resourceMap = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        // 获取启用的权限操作请求
        List<String> permissions = permissionService.list();


        PathMatcher pathMatcher = new AntPathMatcher();
        for (String permission : permissions) {
            if (StringUtils.isNotBlank(permission)) {
//				boolean flag = false;
//				for (String url : ignoredUrlsProperties.getUrls()) {
//					if (pathMatcher.match(url, permission)) {
//						flag = true;
//						break;
//					}
//				}
//				if (flag) {
//					continue;
//				}

                configAttributes = new ArrayList<>();
                cfg = new SecurityConfig(permission);
                //作为MyAccessDecisionManager类的decide的第三个参数
                configAttributes.add(cfg);
                //用权限的path作为map的key，用ConfigAttribute的集合作为value
                resourceMap.put(permission, configAttributes);
            }
        }

    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给decide方法，用来判定用户是否有此权限。如果不在权限表中则放行
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url2 = fi.getRequestUrl();
        String method = fi.getHttpRequest().getMethod();
        String url = method + fi.getRequest().getServletPath();
        if (resourceMap == null) {
            loadResourceDefine();
        }

        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = resourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String resUrl = iterator.next();
            // matches() 方法用于检测字符串是否匹配给定的正则表达式
            if (StringUtils.isNotBlank(resUrl) && pathMatcher.match(resUrl, url)) {
                return resourceMap.get(resUrl);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
