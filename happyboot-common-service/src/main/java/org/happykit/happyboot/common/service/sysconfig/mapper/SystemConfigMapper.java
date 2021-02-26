package org.happykit.happyboot.common.service.sysconfig.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author chen.xudong
 * @date 2020/8/8
 */
public interface SystemConfigMapper {
    @Select("select `value` from sys_config where is_deleted = 0 and `key` = #{key}")
    String getValueByKey(@Param("key") String key);
}
