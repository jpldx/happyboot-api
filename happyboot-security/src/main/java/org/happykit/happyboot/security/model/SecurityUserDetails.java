package org.happykit.happyboot.security.model;

import org.apache.commons.collections4.CollectionUtils;
import org.happykit.happyboot.enums.UserStatusEnum;
import org.happykit.happyboot.security.constants.SecurityConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
public class SecurityUserDetails implements UserDetails {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 账号类型（0/主账号 1/子账号）
     */
    private String userType;
    /**
     * 主账号id
     */
    private String mainAccountId;
    /**
     * url
     */
    private List<String> permissions;
    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 是否是超级管理员
     */
    private Boolean admin;
    /**
     * 登录标识
     */
    private String token;

    public SecurityUserDetails() {
    }

    public SecurityUserDetails(String id, String username, String password, String deptId, String userType, Integer status, List<String> permissions, List<String> roles, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deptId = deptId;
        this.userType = userType;
        this.status = status;
        this.permissions = permissions;
        this.roles = roles;
        this.token = token;
        // 如果是主账号登录，则设置主账号id
        if (SecurityConstant.USER_TYPE_0.equals(userType)) {
            this.mainAccountId = id;
        }
    }

    public SecurityUserDetails(String id, String mainAccountId, String username, String password, String deptId, String userType, Integer status, List<String> permissions, List<String> roles, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deptId = deptId;
        this.userType = userType;
        this.status = status;
        this.permissions = permissions;
        this.roles = roles;
        this.token = token;
        this.mainAccountId = mainAccountId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            List<GrantedAuthority> list = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            authorityList.addAll(list);
        }
        if (CollectionUtils.isNotEmpty(roles)) {
            List<GrantedAuthority> list = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            authorityList.addAll(list);
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return UserStatusEnum.ENABLE.getCode().equals(this.status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(String mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
