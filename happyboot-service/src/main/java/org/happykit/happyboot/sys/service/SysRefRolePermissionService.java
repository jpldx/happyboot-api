package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysRefRolePermissionDO;

import java.util.List;

/**
 * 角色与权限关系 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/03/26
 */
public interface SysRefRolePermissionService extends IService<SysRefRolePermissionDO> {

    /**
     * 通过权限id查询列表
     *
     * @param permissionId
     * @return
     */
    List<SysRefRolePermissionDO> listSysRefRolePermissionsByPermissionId(Long permissionId);

    /**
     * 通过角色id查询列表
     *
     * @param roleId
     * @return
     */
    List<SysRefRolePermissionDO> listSysRefRolePermissionsByRoleId(Long roleId);

    /**
     * 通过权限id删除
     *
     * @param permissionId
     * @return
     */
    boolean deleteByPermissionId(String permissionId);

    /**
     * 通过角色id删除
     *
     * @param roleId
     * @return
     */
    boolean deleteByRoleId(String roleId);
}
