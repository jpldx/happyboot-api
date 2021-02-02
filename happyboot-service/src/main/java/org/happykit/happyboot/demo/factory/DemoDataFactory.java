package org.happykit.happyboot.demo.factory;

import org.happykit.happyboot.demo.model.entity.DemoDataDO;
import org.happykit.happyboot.demo.model.form.DemoDataForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/06/10
 */
@Mapper
public interface DemoDataFactory {

    DemoDataFactory INSTANCE = Mappers.getMapper(DemoDataFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    DemoDataDO form2Do(DemoDataForm form);
}
