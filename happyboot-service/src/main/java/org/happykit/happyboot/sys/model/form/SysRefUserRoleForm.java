package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 角色与账号关系表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysRefUserRoleForm implements Serializable {
    /**
     * 用户id
     */
    @NotNull(message = "用户必须填")
    private String userId;
    /**
     * 角色id集合
     */
    private List<String> roleIds;
    /**
     * 授权类型
     */
    @NotBlank(message = "授权类型必须填")
    private String authType;
}