package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 树状字典提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
public class SysTreeDictForm implements Serializable {
    @NotNull(message = "主键必须填", groups = Update.class)
    private Long id;
    /**
     * 字段名称
     */
    @NotEmpty(message = "字典名称必须填")
    private String dictName;
    /**
     * 字段代号，默认大写加下划线
     */
    @NotEmpty(message = "字典代号必须填")
    private String dictCode;
    /**
     * 描述
     */
    private String code;
    /**
     * 父级字典值
     */
    private String parentCode;
}
