package org.happykit.happyboot.model.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 获取ID集合模型
 *
 * @author shaoqiang
 * @version 1.0 2020/5/18
 */
@Data
public class IdsStatusModel implements Serializable {
    /**
     * ID集合
     */
    @NotEmpty(message = "ID集合必须填")
    private String[] ids;
    /**
     * 状态
     */
    @NotNull(message = "状态必填项")
    private Integer status;
}
