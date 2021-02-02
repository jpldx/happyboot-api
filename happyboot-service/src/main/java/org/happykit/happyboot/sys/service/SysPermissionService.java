package org.happykit.happyboot.sys.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.model.model.ModifyNodeModel;
import org.happykit.happyboot.sys.model.dto.SysPermissionTreeDTO;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.model.form.SysMenuForm;

/**
 * 权限服务接口
 *
 * @author shaoqiang
 * @version 1.0 2020/3/7
 */
public interface SysPermissionService extends IService<SysPermissionDO> {

    /**
     * 权限树
     *
     * @param module  模块
     * @param neTypes 不包含类型
     * @param types   包含类型
     * @return
     */
    List<SysPermissionTreeDTO> tree(@NotBlank String module, String[] neTypes, String[] types);

    /**
     * 通过类型获取权限列表
     *
     * @param type
     * @return
     */
    List<SysPermissionDO> listSysPermissionsByType(@NotBlank String type);

    /**
     * 通过父亲id且只包含type获取权限列表
     *
     * @param parentId
     * @param type
     * @return
     */
    List<SysPermissionDO> listSysPermissionsByParentIdAndType(@NotNull String parentId, @NotBlank String type);

    /**
     * 通过角色id、授权类型、模块获取权限列表
     *
     * @param roleId
     * @param authType
     * @param module
     * @param neTypes  不包含类型
     * @param types    包含类型
     * @return
     */
    List<SysPermissionDO> listSysPermissionsByRoleIdAndAuthTypeAndModule(
            @NotNull String roleId, @NotBlank String authType, @NotBlank String module, String[] neTypes, String[] types);

    /**
     * 通过用户id、角色-用户授权类型、角色-菜单授权类型、模块类型获取权限列表
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
    List<SysPermissionDO> listSysPermissionsByUserIdAndAuthTypeAndModule(
            @NotNull String userId, @NotBlank String roleUserType, @NotBlank String roleMenuType, String parentId, String module, String[] neTypes, String[] types);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysPermissionDO saveSysMenu(SysMenuForm form);

    /**
     * 更新
     *
     * @param form
     * @return
     */
    SysPermissionDO updateSysMenu(SysMenuForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysPermission(@NotNull String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysPermission(String... ids);

    /**
     * 变更节点位置
     *
     * @param form
     * @return
     */
    boolean updateNode(ModifyNodeModel form);

}
