package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysLogDO;
import org.happykit.happyboot.sys.model.form.SysLogForm1;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysLogFactory {

    SysLogFactory INSTANCE = Mappers.getMapper(SysLogFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysLogDO form2Do(SysLogForm1 form);
}
