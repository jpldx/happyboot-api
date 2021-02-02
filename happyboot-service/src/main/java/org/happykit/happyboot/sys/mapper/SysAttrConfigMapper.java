package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysAttrConfigDO;
import org.happykit.happyboot.sys.model.query.SysAttrConfigPageQueryParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper接口
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
public interface SysAttrConfigMapper extends BaseMapper<SysAttrConfigDO> {
    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysAttrConfigDO> selectSysAttrConfigsByPage(Page page, @Param("param") SysAttrConfigPageQueryParam param);

    @Select("SELECT `value` FROM sys_attr_config WHERE `key`= 'BATCH_RUN_SUCCESSFUL_DATE'")
    String querySysAttrConfigTextByKey();

    @Update("UPDATE sys_attr_config SET VALUE = #{now} WHERE `key`='BATCH_RUN_SUCCESSFUL_DATE'")
    void UpdateBatchSuccess(@Param("now") String now);
}
