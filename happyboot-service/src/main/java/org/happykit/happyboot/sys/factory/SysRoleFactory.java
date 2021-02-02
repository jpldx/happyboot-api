package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysRoleDO;
import org.happykit.happyboot.sys.model.form.SysRoleForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Mapper
public interface SysRoleFactory {

    SysRoleFactory INSTANCE = Mappers.getMapper(SysRoleFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysRoleDO form2Do(SysRoleForm form);
}
