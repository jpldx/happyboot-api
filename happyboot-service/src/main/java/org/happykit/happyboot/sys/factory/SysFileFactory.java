package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysFileDO;
import org.happykit.happyboot.sys.model.form.SysFileForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/03/30
 */
@Mapper
public interface SysFileFactory {

    SysFileFactory INSTANCE = Mappers.getMapper(SysFileFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysFileDO form2Do(SysFileForm form);
}
