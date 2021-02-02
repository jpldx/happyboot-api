package org.happykit.happyboot.sys.model.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户id
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/3
 */
@Data
public class SysUserIdForm implements Serializable {
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;
}