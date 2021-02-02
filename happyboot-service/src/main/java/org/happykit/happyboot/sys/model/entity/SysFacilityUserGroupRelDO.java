package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-功能组关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_facility_user_group_rel")
public class SysFacilityUserGroupRelDO extends BaseEntity {
    /**
     * 功能点ID
     */
    private String userId;

    /**
     * 功能组ID
     */
    private String facilityGroupId;
}
