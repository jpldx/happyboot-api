package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 区域部门表
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept_region")
public class SysDeptRegionDO extends BaseEntity {
    /**
     * 父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String parentId;
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 区域编号
     */
    private String regionCode;
    /**
     * 排序
     */
    private Integer orderId;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
}
