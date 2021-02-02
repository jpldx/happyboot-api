package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 角色与权限菜单关系表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysRefRolePermissionForm implements Serializable {
    /**
     * 角色id
     */
    @NotNull(message = "角色必须填")
    private String roleId;
    /**
     * 权限集合
     */
    private List<String> permissionIds;
    /**
     * 授权类型
     */
    @NotBlank(message = "授权类型必须填")
    private String authType;
}