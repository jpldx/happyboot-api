package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysDictDO;
import org.happykit.happyboot.sys.model.form.SysDictForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/3/16
 */
@Mapper
public interface SysDictFactory {

    SysDictFactory INSTANCE = Mappers.getMapper(SysDictFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysDictDO form2Do(SysDictForm form);
}
