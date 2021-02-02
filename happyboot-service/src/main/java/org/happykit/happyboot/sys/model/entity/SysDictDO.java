package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDictDO extends BaseEntity {
    /**
     * 字段名称
     */
    private String dictName;
    /**
     * 字段代号，默认大写加下划线
     */
    private String dictCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
}
