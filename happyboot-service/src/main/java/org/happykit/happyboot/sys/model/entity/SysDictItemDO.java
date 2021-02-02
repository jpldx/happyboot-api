package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/3/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_item")
public class SysDictItemDO extends BaseEntity {
    /**
     * 所属字典id
     */
    private String dictId;
    /**
     * 字典项文本
     */
    private String itemText;
    /**
     * 字典项值
     */
    private String itemValue;
    /**
     * 字典项设置1
     */
    private String attr1;
    /**
     * 字典项设置2
     */
    private String attr2;
    /**
     * 描述
     */
    private String description;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 排序号
     */
    private Integer orderId;
}
