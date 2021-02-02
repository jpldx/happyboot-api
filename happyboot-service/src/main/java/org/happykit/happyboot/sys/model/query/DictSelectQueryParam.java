package org.happykit.happyboot.sys.model.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author shaoqiang
 * @version 1.0 2020/5/20
 */
@Data
public class DictSelectQueryParam implements Serializable {
    /**
     * 字典code
     */
    @NotBlank(message = "字典code不能为空")
    private String dictCode;
}
