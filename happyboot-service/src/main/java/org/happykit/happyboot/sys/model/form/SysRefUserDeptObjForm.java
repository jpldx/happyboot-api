package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户-部门关联提交表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/3
 */
@Data
public class SysRefUserDeptObjForm implements Serializable {
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;
    /**
     * 部门id
     */
    @NotBlank(message = "部门id不能为空")
    private String deptId;
}