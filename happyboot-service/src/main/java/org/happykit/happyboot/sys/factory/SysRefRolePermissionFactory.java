package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysRefRolePermissionDO;
import org.happykit.happyboot.sys.model.form.SysRefRolePermissionForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色与权限菜单关系表对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysRefRolePermissionFactory {

    SysRefRolePermissionFactory INSTANCE = Mappers.getMapper(SysRefRolePermissionFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysRefRolePermissionDO form2Do(SysRefRolePermissionForm form);
}
