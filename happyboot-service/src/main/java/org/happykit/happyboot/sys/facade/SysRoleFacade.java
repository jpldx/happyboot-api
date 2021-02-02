package org.happykit.happyboot.sys.facade;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.happykit.happyboot.constant.SysConstant;
import org.happykit.happyboot.sys.model.entity.*;
import org.happykit.happyboot.sys.model.form.SysRefRolePermissionForm;
import org.happykit.happyboot.sys.model.form.SysRefRoleUserForm;
import org.happykit.happyboot.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author shaoqiang
 * @version 1.0 2020/4/3
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRoleFacade {

    private final SysPermissionService sysPermissionService;
    private final SysRefRolePermissionService sysRefRolePermissionService;
    private final SysRefRoleUserService sysRefRoleUserService;
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRole(String... ids) {
        for (String id : ids) {
            sysRefRoleUserService.deleteByRoleId(id);
            sysRefRolePermissionService.deleteByRoleId(id);
        }
        sysRoleService.deleteSysRole(ids);
        return true;
    }

    /**
     * 保存角色菜单关联 角色1-菜单n
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRolePermission(SysRefRolePermissionForm form) {
        SysRoleDO sysRole = sysRoleService.getById(form.getRoleId());
        if (sysRole == null) {
            return false;
        }
        // 先删除
        sysRefRolePermissionService.deleteByRoleId(form.getRoleId());

        if (CollectionUtils.isEmpty(form.getPermissionIds())) {
            return true;
        }

        Set<String> permissionIdSet = new HashSet<>(form.getPermissionIds());
        List<SysRefRolePermissionDO> entityList = new ArrayList<>();
        permissionIdSet.stream().forEach(permissionId -> {
            SysPermissionDO sysPermission = sysPermissionService.getById(permissionId);
            if (sysPermission != null) {
                // 不等于action，查询只包含action子集
                if (!SysConstant.PERMISSION_KEY.equals(sysPermission.getType())) {
                    List<SysPermissionDO> children = sysPermissionService
                            .listSysPermissionsByParentIdAndType(sysPermission.getId(), SysConstant.PERMISSION_KEY);
                    // 将action子集加入权限集合
                    children.stream().forEach(child -> {
                        SysRefRolePermissionDO entity = new SysRefRolePermissionDO();
                        entity.setRoleId(form.getRoleId());
                        entity.setPermissionId(child.getId());
                        entity.setAuthType(form.getAuthType());
                        entityList.add(entity);
                    });
                }
                SysRefRolePermissionDO entity = new SysRefRolePermissionDO();
                entity.setRoleId(form.getRoleId());
                entity.setPermissionId(permissionId);
                entity.setAuthType(form.getAuthType());
                entityList.add(entity);
            }
        });

        return sysRefRolePermissionService.saveBatch(entityList);
    }

    /**
     * 保存用户角色关联，用户n-角色1
     *
     * @param form
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleUser(SysRefRoleUserForm form) {
        SysRoleDO sysRole = sysRoleService.getById(form.getRoleId());
        if (sysRole == null) {
            return false;
        }
        // 先删除
        sysRefRoleUserService.deleteByRoleId(form.getRoleId());

        if (CollectionUtils.isEmpty(form.getUserIds())) {
            return true;
        }
        Set<String> userIdSet = new HashSet<>(form.getUserIds());
        List<SysRefRoleUserDO> entityList = userIdSet.stream().map(userId -> {
            SysUserDO sysUser = sysUserService.getById(userId);
            if (sysUser == null) {
                return null;
            }
            SysRefRoleUserDO entity = new SysRefRoleUserDO();
            entity.setRoleId(form.getRoleId());
            entity.setUserId(userId);
            entity.setAuthType(form.getAuthType());
            return entity;
        }).filter(item -> item != null).collect(Collectors.toList());
        return sysRefRoleUserService.saveBatch(entityList);
    }

}
