package org.happykit.happyboot.sys.mapper;

import java.util.List;

import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 权限Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
public interface SysPermissionMapper extends BaseMapper<SysPermissionDO> {

    /**
     * 通过角色id、授权类型、模块获取权限列表
     *
     * @param roleId
     * @param authType
     * @param parentId
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    List<SysPermissionDO> selectSysPermissionsByRoleIdAndAuthType(
            @Param("roleId") String roleId, @Param("authType") String authType, @Param("parentId") Long parentId, @Param("module") String module, @Param("neTypes") String[] neTypes, @Param("types") String[] types);

    /**
     * 通过用户id、角色-用户授权类型、角色-菜单授权类型获取权限列表
     *
     * @param userId
     * @param roleUserType
     * @param roleMenuType
     * @param parentId
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    List<SysPermissionDO> selectSysPermissionsByUserIdAndAuthType(
            @Param("userId") String userId, @Param("roleUserType") String roleUserType, @Param("roleMenuType") String roleMenuType, @Param("parentId") String parentId, @Param("module") String module, @Param("neTypes") String[] neTypes, @Param("types") String[] types);

    /**
     * 通过父级id、模块类型获取权限列表
     *
     * @param parentId
     * @param module
     * @param type
     * @param neTypes
     * @param types
     * @return
     */
    List<SysPermissionDO> selectSysPermissions(
            @Param("parentId") String parentId, @Param("module") String module, @Param("type") String type, @Param("neTypes") String[] neTypes, @Param("types") String[] types);

    /**
     * 通过角色id、授权类型获取权限ID列表
     *
     * @param roleId
     * @param authType
     * @param parentId
     * @param module
     * @param neTypes
     * @param types
     * @return
     */
    List<Long> selectIdsByRoleIdAndAuthType(
            @Param("roleId") Long roleId, @Param("authType") String authType, @Param("parentId") Long parentId, @Param("module") String module, @Param("neTypes") String[] neTypes, @Param("types") String[] types);

}
