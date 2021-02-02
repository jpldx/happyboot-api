package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysConfigDO;
import org.happykit.happyboot.sys.model.query.SysConfigPageQueryParam;
import org.apache.ibatis.annotations.Param;


/**
 * 系统配置
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
public interface SysConfigMapper extends BaseMapper<SysConfigDO> {


    String getValueByKey(@Param("key") String key);

    IPage<SysConfigDO> page(Page page, @Param("query") SysConfigPageQueryParam query);

    SysConfigDO get(@Param("id") String id);

    Integer add(@Param("entity") SysConfigDO entity);

    Integer update(@Param("entity") SysConfigDO entity);

    SysConfigDO getByKey(@Param("key") String key);
}
