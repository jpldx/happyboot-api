package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysRefRoleUserDO;
import org.happykit.happyboot.sys.model.form.SysRefRoleUserForm;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色与账号关系表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefRoleUserService extends IService<SysRefRoleUserDO> {

    /**
     * 通过账号id查询列表
     *
     * @param userId
     * @return
     */
    List<SysRefRoleUserDO> listSysRefRoleUsersByUserId(Long userId);

    /**
     * 通过角色id查询列表
     *
     * @param roleId
     * @return
     */
    List<SysRefRoleUserDO> listSysRefRoleUsersByRoleId(Long roleId);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysRefRoleUserDO saveSysRefRoleUser(SysRefRoleUserForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysRefRoleUserDO updateSysRefRoleUser(SysRefRoleUserForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysRefRoleUser(@NotNull Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysRefRoleUser(Long... ids);

    /**
     * 通过账号id删除
     *
     * @param userId
     * @return
     */
    boolean deleteByUserId(String userId);

    /**
     * 通过角色id删除
     *
     * @param roleId
     * @return
     */
    boolean deleteByRoleId(String roleId);
}
