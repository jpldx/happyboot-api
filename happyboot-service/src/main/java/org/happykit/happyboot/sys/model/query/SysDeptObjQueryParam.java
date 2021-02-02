package org.happykit.happyboot.sys.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 对象内部部门表分页查询对象
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysDeptObjQueryParam implements Serializable {
    /**
     *
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编码
     */
    private String deptCode;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 主体id
     */
    private String subjectId;
}
