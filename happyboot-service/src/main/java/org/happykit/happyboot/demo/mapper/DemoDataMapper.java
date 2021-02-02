package org.happykit.happyboot.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.demo.model.entity.DemoDataDO;
import org.happykit.happyboot.demo.model.query.DemoDataPageQueryParam;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口
 *
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
public interface DemoDataMapper extends BaseMapper<DemoDataDO> {
    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DemoDataDO> selectDemoDatasByPage(Page page, @Param("param") DemoDataPageQueryParam param);
}
