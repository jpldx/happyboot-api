package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对象内部部门表
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept_obj")
public class SysDeptObjDO extends BaseEntity {
    /**
     * 父级部门id
     */
    private String parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编码
     */
    private String deptCode;
    /**
     * 排序号
     */
    private Integer orderId;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 是否综治中心（0/否 1/是）
     */
    private String isLeadDept;
}
