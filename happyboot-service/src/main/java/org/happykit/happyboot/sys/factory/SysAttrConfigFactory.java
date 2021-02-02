package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysAttrConfigDO;
import org.happykit.happyboot.sys.model.form.SysAttrConfigForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Mapper
public interface SysAttrConfigFactory {

    SysAttrConfigFactory INSTANCE = Mappers.getMapper(SysAttrConfigFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysAttrConfigDO form2Do(SysAttrConfigForm form);
}
