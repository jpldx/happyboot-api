package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户与区域关系表提交类
 *
 * @author chen.xudong
 * @since 2020/6/16
 */
@Data
public class SysRefUserDeptRegionForm implements Serializable {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private String userId;
    /**
     * 区域ids
     */
    private List<Long> regionIds;
}