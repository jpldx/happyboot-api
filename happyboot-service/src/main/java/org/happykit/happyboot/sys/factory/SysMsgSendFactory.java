package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.sys.model.entity.SysMsgSendDO;
import org.happykit.happyboot.sys.model.form.SysMsgSendForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户通告阅读标记表对象转换工厂
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Mapper
public interface SysMsgSendFactory {

    SysMsgSendFactory INSTANCE = Mappers.getMapper(SysMsgSendFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysMsgSendDO form2Do(SysMsgSendForm form);
}
