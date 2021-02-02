package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysSubjectDO;
import org.happykit.happyboot.sys.model.form.SysSubjectForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 主体对象转换工厂
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Mapper
public interface SysSubjectFactory {

    SysSubjectFactory INSTANCE = Mappers.getMapper(SysSubjectFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysSubjectDO form2Do(SysSubjectForm form);
}
