package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 区域部门表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysDeptRegionForm implements Serializable {
    /**
     * 主键id
     */
    @NotNull(message = "主键必须填", groups = Update.class)
    private Long id;
    /**
     * 父ID
     */
    @NotNull(message = "父ID必须填")
    private Long parentId;
    /**
     * 区域名称
     */
    @NotEmpty(message = "区域名称必须填")
    private String regionName;
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
}