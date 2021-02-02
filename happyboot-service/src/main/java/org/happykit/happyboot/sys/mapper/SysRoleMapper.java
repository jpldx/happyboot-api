package org.happykit.happyboot.sys.mapper;

import java.util.List;

import org.happykit.happyboot.sys.model.entity.SysRoleDO;
import org.happykit.happyboot.sys.model.query.SysRolePageQueryParam;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 系统角色表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    /**
     * 通过authorityName获取对象
     *
     * @param authorityName
     * @return
     */
    SysRoleDO selectByAuthorityName(@Param("authorityName") String authorityName);

    /**
     * 通过用户id、授权类型获取角色列表
     *
     * @param userId
     * @param authType
     * @return
     */
    List<SysRoleDO> selectSysRolesByUserIdAndAuthType(@Param("userId") String userId, @Param("authType") String authType);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysRoleDO> selectSysRolesByPage(Page page, @Param("param") SysRolePageQueryParam param);

    // /**
    // * 查询角色列表
    // *
    // * @return
    // */
    // List<SysRoleDO> listSysRoles();

}
