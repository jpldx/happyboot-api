package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能参数表单
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/20
 */
@Data
public class SysFacilityParamForm implements Serializable {

    /**
     * 功能ID
     */
    @NotBlank(message = "功能ID不能为空")
    private String facilityId;
    /**
     * 功能定义（sys/系统定义 group/功能组定义 user/用户定义 默认sys）
     */
    @NotBlank(message = "功能定义不能为空")
    private String setFrom = "sys";
    /**
     * 功能组定义
     */
    private String facilityGroupId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 功能参数ID
     */
    private String facilityParamId;
    /**
     * 功能参数
     */
    @NotBlank(message = "功能参数不能为空")
    private String facilityParam;
}