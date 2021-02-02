package org.happykit.happyboot.model.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 获取ID模型
 *
 * @author shaoqiang
 * @version 1.0 2020/5/18
 */
@Data
public class IdModel implements Serializable {
    /**
     * 主键ID
     */
    @NotBlank(message = "主键必须填")
    private String id;
}
