package org.happykit.happyboot.op.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.happykit.happyboot.op.model.entity.OpBannerDO;
import org.happykit.happyboot.op.model.form.OpBannerForm;
import org.happykit.happyboot.op.model.query.OpBannerPageQuery;

import javax.validation.constraints.NotNull;

/**
 * 轮播图
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
public interface OpBannerService extends IService<OpBannerDO> {

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    IPage<OpBannerDO> page(OpBannerPageQuery query);

    /**
     * 新增
     *
     * @param form
     * @return
     */
    boolean add(OpBannerForm form);

    /**
     * 编辑
     *
     * @param form
     * @return
     */
    boolean update(OpBannerForm form);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean deleteBaseBanner(@NotNull String id);


    /**
     * 查询
     *
     * @param id
     * @return
     */
    OpBannerDO get(String id);
}

