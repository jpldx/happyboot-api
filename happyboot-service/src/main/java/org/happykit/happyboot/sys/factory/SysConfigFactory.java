package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysConfigDO;
import org.happykit.happyboot.sys.model.form.SysConfigForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/06/02
 */
@Mapper
public interface SysConfigFactory {

    SysConfigFactory INSTANCE = Mappers.getMapper(SysConfigFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysConfigDO form2Do(SysConfigForm form);
}
