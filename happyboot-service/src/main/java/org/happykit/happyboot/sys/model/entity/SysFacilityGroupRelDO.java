package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能组-功能点关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_facility_group_rel")
public class SysFacilityGroupRelDO extends BaseEntity {
    /**
     * 功能组ID
     */
    private String facilityGroupId;
    /**
     * 功能点ID
     */
    private String facilityId;
}
