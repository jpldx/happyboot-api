package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.query.SysUserPageQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户账号表 Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    /**
     * 通过username获取账号对象
     *
     * @param username
     * @return
     */
    SysUserDO selectByUsername(@Param("username") String username);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysUserDO> selectSysUsersByPage(Page page, @Param("param") SysUserPageQueryParam param);

    /**
     * 更改系统账号的密码
     *
     * @param entity
     * @return
     */
    int updatePassword(SysUserDO entity);

    /**
     * 更新用户登录信息
     *
     * @param entity
     * @return
     */
    int updateLoginInfo(SysUserDO entity);

    List<SysUserDO> getMainAccountList(@Param("keyword") String keyword);

    /**
     * 根据id获取昵称
     *
     * @param id
     * @return
     */
    String getNicknameById(@Param("id") String id);
}
