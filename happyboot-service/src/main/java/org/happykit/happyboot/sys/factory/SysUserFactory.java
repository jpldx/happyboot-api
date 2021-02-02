package org.happykit.happyboot.sys.factory;


import org.happykit.happyboot.sys.model.entity.SysUserDO;
import org.happykit.happyboot.sys.model.form.SysUserForm;
import org.happykit.happyboot.sys.model.form.SysUserPwdForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户账号表对象转换工厂
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Mapper
public interface SysUserFactory {

    SysUserFactory INSTANCE = Mappers.getMapper(SysUserFactory.class);

    /**
     * 表单转实体对象
     *
     * @param form
     * @return
     */
    SysUserDO form2Do(SysUserForm form);

    /**
     * 修改密码表单转实体对象
     *
     * @param form
     * @return
     */
    SysUserDO pwdForm2Do(SysUserPwdForm form);

//	/**
//	 * do转换dto
//	 * @param entity
//	 * @return
//	 */
//	SysUserDTO do2Dto(SysUserDO entity);

}
