package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysTreeDictDO;
import org.happykit.happyboot.sys.model.form.SysTreeDictForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/3/16
 */
@Mapper
public interface SysTreeDictFactory {

    SysTreeDictFactory INSTANCE = Mappers.getMapper(SysTreeDictFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysTreeDictDO form2Do(SysTreeDictForm form);
}
