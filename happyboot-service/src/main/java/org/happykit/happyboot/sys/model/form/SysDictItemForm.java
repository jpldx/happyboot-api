package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字典提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
public class SysDictItemForm implements Serializable {
    @NotNull(message = "主键必须填", groups = Update.class)
    private Long id;
    /**
     * 所属字典id
     */
    @NotNull(message = "所属字典必须填")
    private Long dictId;
    /**
     * 字典项文本
     */
    @NotEmpty(message = "字典项文本必须填")
    private String itemText;
    /**
     * 字典项值
     */
    @NotEmpty(message = "字典项值必须填")
    private String itemValue;
    /**
     * 描述
     */
    private String description;
    /**
     * 0=禁用 1=启用
     */
    @NotNull(message = "状态必须填")
    private Integer status;
    /**
     * 排序号
     */
    @NotNull(message = "排序号必须填")
    private Integer orderId;
}
