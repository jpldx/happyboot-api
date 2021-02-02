package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 对象区域关系表
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@TableName("sys_ref_region_obj")
public class SysRefRegionObjDO implements Serializable {
    /**
     * 主键id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 单位id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long objId;
    /**
     * 区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long regionId;
    /**
     * 区域Code
     */
    private String regionCode;
}
