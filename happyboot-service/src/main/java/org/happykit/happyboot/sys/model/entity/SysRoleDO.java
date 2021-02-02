package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色表
 *
 * @author shaoqiang
 * @version 1.0 2020/3/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRoleDO extends BaseEntity {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色代码
     */
    private String authorityName;
    /**
     * 角色类型（OBJ_ROLE：对象内部角色   OTHER_ROLE： 其它角色）
     */
    private String roleType;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 是否是系统级别，系统级别不允许操作
     */
    @TableField(value = "is_sys")
    private Boolean sys;

}
