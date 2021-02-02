package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树状字典
 *
 * @author shaoqiang
 * @version 1.0 2020/5/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tree_dict")
public class SysTreeDictDO extends BaseEntity {
    /**
     * 字段名称
     */
    private String dictName;
    /**
     * 字段代号，默认大写加下划线
     */
    private String dictCode;
    /**
     * 字典值
     */
    private String code;
    /**
     * 父级字典值
     */
    private String parentCode;
}
