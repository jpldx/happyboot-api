package org.happykit.happyboot.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.sys.model.entity.SysUpdateDO;
import org.happykit.happyboot.sys.model.query.SysUpdatePageQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
public interface SysUpdateMapper extends BaseMapper<SysUpdateDO> {
    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SysUpdateDO> selectSysUpdatesByPage(Page page, @Param("param") SysUpdatePageQueryParam param);
}
