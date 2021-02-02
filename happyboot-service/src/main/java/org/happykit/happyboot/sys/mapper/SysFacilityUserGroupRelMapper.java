package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityUserGroupRelDO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户-功能组关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityUserGroupRelMapper extends BaseMapper<SysFacilityUserGroupRelDO> {

    int deleteByUserId(@Param("userId") String userId);
}
