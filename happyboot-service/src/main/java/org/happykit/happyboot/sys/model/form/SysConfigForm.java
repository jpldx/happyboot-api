package org.happykit.happyboot.sys.model.form;

import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统配置表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
@Data
public class SysConfigForm implements Serializable {
    /**
     * ID
     */
    @NotBlank(groups = Update.class, message = "ID不能为空")
    private String id;
    /**
     * 参数名
     */
    @NotBlank(message = "参数名不能为空")
    private String key;
    /**
     * 参数值
     */
    @NotBlank(message = "参数值不能为空")
    private String value;
    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空")
    private String type;
    /**
     * 备注
     */
    private String remark;
}