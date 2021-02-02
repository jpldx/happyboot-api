package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.dto.SysPermissionTreeDTO;
import org.happykit.happyboot.sys.model.entity.SysPermissionDO;
import org.happykit.happyboot.sys.model.form.SysMenuForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Mapper
public interface SysPermissionFactory {
    SysPermissionFactory INSTANCE = Mappers.getMapper(SysPermissionFactory.class);

//	/**
//	 * 表单转实体对象
//	 * @param form
//	 * @return
//	 */
//	SysPermissionDO form2Do(SysPermissionForm form);

    /**
     * 实体对象转树状对象
     *
     * @param entity
     * @return
     */
    SysPermissionTreeDTO do2Node(SysPermissionDO entity);

    /**
     * 菜单转权限
     *
     * @param form
     * @return
     */
    SysPermissionDO menu2Do(SysMenuForm form);

}
