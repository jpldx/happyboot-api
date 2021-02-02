package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysRefRoleUserDO;
import org.happykit.happyboot.sys.model.form.SysRefRoleUserForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色与账号关系表对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysRefRoleUserFactory {

    SysRefRoleUserFactory INSTANCE = Mappers.getMapper(SysRefRoleUserFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysRefRoleUserDO form2Do(SysRefRoleUserForm form);
}
