package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysDictItemDO;
import org.happykit.happyboot.sys.model.form.SysDictItemForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/3/16
 */
@Mapper
public interface SysDictItemFactory {

    SysDictItemFactory INSTANCE = Mappers.getMapper(SysDictItemFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysDictItemDO form2Do(SysDictItemForm form);
}
