package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysDeptObjDO;
import org.happykit.happyboot.sys.model.form.SysDeptObjForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象内部部门表对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysDeptObjFactory {

    SysDeptObjFactory INSTANCE = Mappers.getMapper(SysDeptObjFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysDeptObjDO form2Do(SysDeptObjForm form);
}
