package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 角色与账号关系表提交对象
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysRefRoleUserForm implements Serializable {
    /**
     * 角色id
     */
    @NotNull(message = "角色必须填")
    private String roleId;
    /**
     * 用户id集合
     */
    private List<String> userIds;
    /**
     * 授权类型
     */
    @NotBlank(message = "授权类型必须填")
    private String authType;
}