package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysMsgDO;
import org.happykit.happyboot.sys.model.form.SysMsgForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统通告表对象转换工厂
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Mapper
public interface SysMsgFactory {

    SysMsgFactory INSTANCE = Mappers.getMapper(SysMsgFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysMsgDO form2Do(SysMsgForm form);
}
