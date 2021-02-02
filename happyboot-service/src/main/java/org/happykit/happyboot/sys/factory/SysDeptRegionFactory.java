package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysDeptRegionDO;
import org.happykit.happyboot.sys.model.form.SysDeptRegionForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 区域部门表对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysDeptRegionFactory {

    SysDeptRegionFactory INSTANCE = Mappers.getMapper(SysDeptRegionFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysDeptRegionDO form2Do(SysDeptRegionForm form);
}
