package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 功能-功能参数关系
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_facility_param_rel")
public class SysFacilityParamRelDO extends BaseEntity {
    /**
     * 功能ID
     */
    private String facilityId;
    /**
     * 功能参数ID
     */
    private String facilityParamId;
    /**
     * 功能组ID
     */
    private String facilityGroupId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 定义类型
     */
    private String setFrom;
}
