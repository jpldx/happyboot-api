package org.happykit.happyboot.sys.mapper;

import java.util.List;

import org.happykit.happyboot.sys.model.entity.SysRefRolePermissionDO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 角色与权限关系表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/03/26
 */
public interface SysRefRolePermissionMapper extends BaseMapper<SysRefRolePermissionDO> {

    /**
     * 通过权限id查询
     *
     * @param permissionId
     * @return
     */
    List<SysRefRolePermissionDO> selectSysRefRolePermissionsByPermissionId(@Param("permissionId") Long permissionId);

    /**
     * 通过角色id查询
     *
     * @param roleId
     * @return
     */
    List<SysRefRolePermissionDO> selectSysRefRolePermissionsByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过权限id删除
     *
     * @param permissionId
     * @return
     */
    int deleteByPermissionId(@Param("permissionId") String permissionId);

    /**
     * 通过角色id删除
     *
     * @param roleId
     * @return
     */
    int deleteByRoleId(@Param("roleId") String roleId);

}
