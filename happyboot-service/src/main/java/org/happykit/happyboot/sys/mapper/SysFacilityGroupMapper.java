package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysFacilityGroupDO;
import org.happykit.happyboot.sys.model.query.SysFacilityGroupPageQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 功能组
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface SysFacilityGroupMapper extends BaseMapper<SysFacilityGroupDO> {

    IPage<SysFacilityGroupDO> page(Page page, @Param("query") SysFacilityGroupPageQueryParam query);

    Set<String> getFacilityGroupIdsByUserId(@Param("userId") String userId);
}
