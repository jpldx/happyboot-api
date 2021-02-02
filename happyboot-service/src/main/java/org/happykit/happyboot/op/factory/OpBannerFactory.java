package org.happykit.happyboot.op.factory;

import org.happykit.happyboot.op.model.entity.OpBannerDO;
import org.happykit.happyboot.op.model.form.OpBannerForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 轮播图
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Mapper
public interface OpBannerFactory {

    OpBannerFactory INSTANCE = Mappers.getMapper(OpBannerFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    OpBannerDO form2Do(OpBannerForm form);
}
