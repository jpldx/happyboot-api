package org.happykit.happyboot.sys.model.form;

import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 主体表单提交类
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/9
 */
@Data
public class SysSubjectForm implements Serializable {
    /**
     * id
     */
    @NotBlank(message = "id不能为空", groups = Update.class)
    private String id;
    /**
     * 主体名称
     */
    @NotBlank(message = "主体名称不能为空")
    private String subjectName;
}