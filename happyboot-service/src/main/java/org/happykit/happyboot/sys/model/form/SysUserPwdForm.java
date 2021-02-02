package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 账号状态提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/13
 */
@Data
public class SysUserPwdForm implements Serializable {

    @NotNull(message = "主键必须填")
    private Long id;

    @NotEmpty(message = "密码必须填")
    @Size(min = 6, max = 16, message = "密码6~16位")
    private String password;

    @NotEmpty(message = "确认密码必须填")
    private String passwordConfirm;
}
