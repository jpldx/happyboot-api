package org.happykit.happyboot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.entity.SysUserRelDO;

import java.util.List;

/**
 * 用户账号关联表
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/2
 */
public interface SysUserRelService extends IService<SysUserRelDO> {

    /**
     * 添加账号关联
     *
     * @param mainUserId 主账号id
     * @param subUserId  子账号id
     */
    void addRel(String mainUserId, String subUserId);

    /**
     * 根据用户id删除账号关联
     * 账号类型为主账号：根据主账号id删除
     * 账号类型为子账号：根据子账号id删除
     *
     * @param userId   用户id
     * @param userType 用户账号类型
     */
    void delRel(String userId, String userType);

    /**
     * 根据用户id查询关联的账号列表
     * 账号类型为主账号：查询关联的子账号列表
     * 账号类型为子账号：查询关联的主账号
     *
     * @param userId   用户id
     * @param userType 用户账号类型
     * @return
     */
    List<SysUserDO> getUserRelListByUserId(String userId, String userType);
}
