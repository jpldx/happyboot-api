package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.happykit.happyboot.sys.model.entity.SysRefRoleUserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与账号关系表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysRefRoleUserMapper extends BaseMapper<SysRefRoleUserDO> {
    /**
     * 通过账号id查询
     *
     * @param userId
     * @return
     */
    List<SysRefRoleUserDO> selectSysRefRoleUsersByUserId(@Param("userId") Long userId);

    /**
     * 通过角色id查询
     *
     * @param roleId
     * @return
     */
    List<SysRefRoleUserDO> selectSysRefRoleUsersByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过账号id删除
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 通过角色id删除
     *
     * @param roleId
     * @return
     */
    int deleteByRoleId(@Param("roleId") String roleId);
}
