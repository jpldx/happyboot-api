package org.happykit.happyboot.sys.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysRoleDO;
import org.happykit.happyboot.sys.model.form.SysRoleForm;
import org.happykit.happyboot.sys.model.query.SysRolePageQueryParam;

/**
 * 系统角色 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
public interface SysRoleService extends IService<SysRoleDO> {

    /**
     * 通过用户id、授权类型获取角色列表
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> listSysRolesByUserIdAndAuthType(@NotNull String userId, @NotBlank String authType);

    /**
     * 通过用户id、授权类型获取角色代码列表
     *
     * @param userId
     * @param authType
     * @return
     */
    List<String> listAuthorityNamesByUserIdAndAuthType(@NotNull String userId, @NotBlank String authType);

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysRoleDO> listSysRolesByPage(SysRolePageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysRoleDO saveSysRole(SysRoleForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysRoleDO updateSysRole(SysRoleForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysRole(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysRole(String... ids);

}
