package org.happykit.happyboot.model.base;

import lombok.Data;

/**
 * 数据权限基类
 *
 * @author chen.xudong
 * @date 2020/7/29
 */
@Data
public class BaseAuthModel {
    /**
     * 对象id
     */
    private String objectId;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门code
     */
    private String deptCode;
}
