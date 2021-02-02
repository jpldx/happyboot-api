package org.happykit.happyboot.sys.model.form;

import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色提交对象
 *
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Data
public class SysRoleForm implements Serializable {
    /**
     * id
     */
    @NotNull(message = "主键必须填", groups = Update.class)
    private Long id;
    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称必须填")
    private String roleName;
    /**
     * 角色代码
     */
    @NotEmpty(message = "角色代码必须填")
    private String authorityName;
    /**
     * 角色类型（OBJ_ROLE：对象内部角色   OTHER_ROLE： 其它角色）
     */
    private String roleType;
    /**
     * 0=禁用 1=启用
     */
    @NotNull(message = "状态必须填")
    private Integer status;
}
