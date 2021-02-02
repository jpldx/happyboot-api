package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamRelDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 功能点-功能点参数关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityParamRelMapper extends BaseMapper<SysFacilityParamRelDO> {

    @Delete("delete from sys_facility_param_rel where facility_param_id = #{facilityParamId}")
    int delete(@Param("facilityParamId") String facilityParamId);
}
