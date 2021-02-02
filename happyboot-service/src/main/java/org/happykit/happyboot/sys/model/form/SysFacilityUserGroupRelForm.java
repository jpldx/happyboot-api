package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户-功能组关联表单
 *
 * @author chen.xudong
 * @since 2020/6/16
 */
@Data
public class SysFacilityUserGroupRelForm implements Serializable {
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    /**
     * 功能组IDS
     */
    private Set<String> facilityGroupIds;
}