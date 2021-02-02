package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 功能组-功能关联表单
 *
 * @author chen.xudong
 * @since 2020/6/16
 */
@Data
public class SysFacilityGroupRelForm implements Serializable {
    /**
     * 功能组ID
     */
    @NotBlank(message = "功能组ID不能为空")
    private String facilityGroupId;
    /**
     * 功能IDS
     */
    private Set<String> facilityIds;
}