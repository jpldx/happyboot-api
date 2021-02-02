package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.form.SysUserForm;
import org.happykit.happyboot.sys.model.form.SysUserPwdForm;
import org.happykit.happyboot.sys.model.form.SysUserStatusForm;
import org.happykit.happyboot.sys.model.query.SysUserPageQueryParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户账号表 服务接口类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysUserService extends IService<SysUserDO> {
    /**
     * 通过username获取对象
     *
     * @param username
     * @return
     */
    SysUserDO getByUsername(@NotBlank String username);

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    IPage<SysUserDO> listSysUsersByPage(SysUserPageQueryParam param);

    /**
     * 保存
     *
     * @param form
     * @return
     */
    SysUserDO saveSysUser(SysUserForm form);

    /**
     * 修改
     *
     * @param form
     * @return
     */
    SysUserDO updateSysUser(SysUserForm form);

    /**
     * 更改系统账号密码
     *
     * @param form
     * @return
     */
    boolean updateSysUserPwd(SysUserPwdForm form);

    /**
     * 更改系统账号状态
     *
     * @param form
     * @return
     */
    boolean updateSysUserStatus(SysUserStatusForm form);

    /**
     * 更新账号登录状态
     *
     * @param entity
     * @return
     */
    boolean updateSysUserLoginInfo(SysUserDO entity);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteSysUser(@NotNull String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysUser(String... ids);

    /**
     * 查询主账号列表
     *
     * @param keyword 搜索关键词
     * @return
     */
    List<SysUserDO> getMainAccountList(String keyword);

    /**
     * 根据id获取昵称
     *
     * @param id
     * @return
     */
    String getNicknameById(String id);

}
