package org.happykit.happyboot.op.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.happykit.happyboot.op.model.entity.OpBannerDO;
import org.happykit.happyboot.op.model.query.OpBannerPageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 轮播图
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface OpBannerMapper extends BaseMapper<OpBannerDO> {
    /**
     * 分页列表
     *
     * @param page
     * @param query
     * @return
     */
    IPage<OpBannerDO> page(Page page, @Param("query") OpBannerPageQuery query);
}
