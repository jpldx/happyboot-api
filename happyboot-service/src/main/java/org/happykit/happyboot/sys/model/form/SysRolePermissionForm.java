package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 角色权限提交对象
 *
 * @author shaoqiang
 * @version 1.0 2020/3/27
 */
@Data
public class SysRolePermissionForm implements Serializable {
    /**
     * 角色id
     */
    @NotNull(message = "角色必须填")
    private Long roleId;
    /**
     * 权限集合
     */
    private List<Long> permissionIds;
}
