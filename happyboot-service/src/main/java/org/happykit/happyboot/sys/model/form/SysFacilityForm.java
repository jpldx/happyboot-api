package org.happykit.happyboot.sys.model.form;

import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
public class SysFacilityForm implements Serializable {
    /**
     * 功能ID
     */
    @NotBlank(groups = Update.class)
    private String id;
    /**
     * 功能名称
     */
    @NotBlank(message = "功能名称不能为空")
    private String facilityName;
    /**
     * 功能平台
     */
    @NotBlank(message = "功能平台不能为空")
    private String facilityPlatform;
    /**
     * 功能标识
     */
    private String facilityKey;
    /**
     * 功能类型
     */
    @NotBlank(message = "功能类型不能为空")
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