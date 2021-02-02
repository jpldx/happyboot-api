package org.happykit.happyboot.sys.factory;

import org.happykit.happyboot.op.model.entity.OpKnowledgeDO;
import org.happykit.happyboot.op.model.form.OpKnowledgeForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author cly
 * @version 1.0 2020/07/01
 */
@Mapper
public interface SysKnowledgeFactory {

    SysKnowledgeFactory INSTANCE = Mappers.getMapper(SysKnowledgeFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    OpKnowledgeDO form2Do(OpKnowledgeForm form);
}
