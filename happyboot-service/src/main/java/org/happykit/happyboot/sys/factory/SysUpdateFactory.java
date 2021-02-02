package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysUpdateDO;
import org.happykit.happyboot.sys.model.form.SysUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Mapper
public interface SysUpdateFactory {

    SysUpdateFactory INSTANCE = Mappers.getMapper(SysUpdateFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysUpdateDO form2Do(SysUpdateForm form);
}
