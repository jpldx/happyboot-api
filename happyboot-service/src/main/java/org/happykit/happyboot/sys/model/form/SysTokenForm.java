package org.happykit.happyboot.sys.model.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 令牌
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/3/1
 */
@Data
public class SysTokenForm implements Serializable {
    /**
     * 令牌
     */
    @NotBlank(message = "令牌不能为空")
    private String token;
}