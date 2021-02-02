package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.entity.SysUserRelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户账号关联表
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/2
 */
public interface SysUserRelMapper extends BaseMapper<SysUserRelDO> {


    List<SysUserDO> getUserRelListByUserId(@Param("userId") String userId, @Param("userType") String userType);

    void delRel(@Param("userId") String userId, @Param("userType") String userType);

}
