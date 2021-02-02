package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.happykit.happyboot.sys.model.entity.SysFacilityParamDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 功能点参数
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityParamMapper extends BaseMapper<SysFacilityParamDO> {

    @Delete("delete from sys_facility_param where id = #{id}")
    int delete(@Param("id") String id);
}
