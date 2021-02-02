package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupRelDO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 功能组-功能点关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityGroupRelMapper extends BaseMapper<SysFacilityGroupRelDO> {

    Set<String> getFacilityIdsByGroupId(@Param("groupId") String groupId);

    int deleteByFacilityGroupId(@Param("groupId") String groupId);
}
