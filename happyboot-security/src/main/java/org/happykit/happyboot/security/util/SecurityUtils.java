package org.happykit.happyboot.security.util;

import org.happykit.happyboot.security.model.SecurityUserDetails;
import org.happykit.happyboot.security.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Security帮助类
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
@Component
public class SecurityUtils {

    @Autowired
    private TokenProperties tokenProperties;


    /**
     * 获取当前用户
     *
     * @return
     */
    public Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public Object getCurrentPrincipal() {
        return getCurrentUserAuthentication().getPrincipal();
    }

    /**
     * 获取当前登录用户对象
     *
     * @return
     */
    public SecurityUserDetails getCurrentUserDetails() {
        Authentication authentication = getCurrentUserAuthentication();
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (SecurityUserDetails) authentication.getPrincipal();
            }
        }
        return null;
    }


    /**
     * 获取当前登录用户id
     *
     * @return
     */
    public String getCurrentUserId() {
        SecurityUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getId();
        }
        return "";
    }

    /**
     * 获取当前登录用户部门id
     *
     * @return
     */
    public String getCurrentUserDeptId() {
        SecurityUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getDeptId();
        }
        return null;
    }

    /**
     * 获取当前登录用户的token
     *
     * @return
     */
    public String getCurrentUserToken() {
        SecurityUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getToken();
        }
        return null;
    }

    /**
     * 获取用户客户端id
     *
     * @param request
     * @return
     */
    public String getClientId(HttpServletRequest request) {
        return request.getHeader(tokenProperties.getClientId());
    }
}
