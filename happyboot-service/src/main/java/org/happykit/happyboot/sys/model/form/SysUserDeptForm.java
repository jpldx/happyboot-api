package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新当前登录用户所属部门ID
 *
 * @author shaoqiang
 * @version 1.0 2020/6/22
 */
@Data
public class SysUserDeptForm implements Serializable {
    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空")
    private String deptId;
}
