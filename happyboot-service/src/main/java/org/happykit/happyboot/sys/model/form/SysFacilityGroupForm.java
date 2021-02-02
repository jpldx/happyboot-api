package org.happykit.happyboot.sys.model.form;

import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能组表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
public class SysFacilityGroupForm implements Serializable {

    /**
     * 功能组ID
     */
    @NotBlank(groups = Update.class)
    private String id;
    /**
     * 功能组名称
     */
    @NotBlank(message = "功能组名称不能为空")
    private String facilityGroupName;
}