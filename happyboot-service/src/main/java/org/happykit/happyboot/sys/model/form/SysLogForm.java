package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/25
 */
@Data
public class SysLogForm {

    /**
     * 日志类型（sys/系统日志 biz/业务日志）
     */
    @NotBlank(message = "类型不能为空")
    private String type;
}
