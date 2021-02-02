package org.happykit.happyboot.model.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移动节点模型对象
 *
 * @author shaoqiang
 * @version 1.0 2020/6/18
 */
@Data
public class ModifyNodeModel implements Serializable {
    /**
     * 主键id
     */
    @NotBlank(message = "主键必须填")
    private String id;
    /**
     * 父亲
     */
    @NotBlank(message = "父ID必须填")
    private String parentId;
}
