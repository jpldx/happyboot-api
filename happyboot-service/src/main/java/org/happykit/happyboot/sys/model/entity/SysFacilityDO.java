package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能点
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_facility")
public class SysFacilityDO extends BaseEntity {
    /**
     * 功能名称
     */
    private String facilityName;
    /**
     * 功能平台
     */
    private String facilityPlatform;
    /**
     * 功能标识
     */
    private String facilityKey;
    /**
     * 功能类型
     */
    private String facilityType;
    /**
     * 功能图标
     */
    private String facilityIcon;
    /**
     * 功能描述
     */
    private String des;
}
