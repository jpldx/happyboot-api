package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 对象内部部门表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysDeptObjForm implements Serializable {
    /**
     * 主键id
     */
    @NotNull(message = "主键必须填", groups = Update.class)
    private String id;
    /**
     * 父ID
     */
    @NotNull(message = "父ID必须填")
    private String parentId;
    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称必须填")
    private String deptName;
    /**
     * 排序号
     */
    @NotNull(message = "排序号必须填")
    private Integer orderId;
    /**
     * 0=禁用 1=启用
     */
    @NotNull(message = "状态必须填")
    private Integer status;
    /**
     * 是否是综治中心（0/否 1/是）
     */
    private String isLeadDept;

}