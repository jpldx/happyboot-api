package org.happykit.happyboot.security.permission;//package com.litong.ltitframework.boot.core.security.permission;

import java.util.List;

/**
 * api权限服务接口
 */
public interface PermissionService {
    /**
     * 查询所有url权限
     *
     * @return
     */
    List<String> list();

}
