package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysFacilityDO;
import org.happykit.happyboot.sys.model.form.SysFacilityParamForm;
import org.happykit.happyboot.sys.model.query.SysFacilityPageQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 功能点
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityMapper extends BaseMapper<SysFacilityDO> {

    IPage<SysFacilityDO> page(Page page, @Param("query") SysFacilityPageQueryParam query);

    SysFacilityParamForm getParam(@Param("id") String id,
                                  @Param("setFrom") String setFrom,
                                  @Param("facilityGroupId") String facilityGroupId,
                                  @Param("userId") String userId);

    Set<String> getFacilityIdsByUserId(@Param("userId") String userId);
}
