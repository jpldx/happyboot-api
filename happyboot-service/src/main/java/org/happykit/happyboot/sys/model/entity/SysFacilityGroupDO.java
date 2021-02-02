package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能组
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_facility_group")
public class SysFacilityGroupDO extends BaseEntity {
    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 功能组名称
     */
    private String facilityGroupName;
}
