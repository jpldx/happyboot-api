package org.happykit.happyboot.enums;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author shaoqiang
 * @version 1.0 2020/3/30
 */
@Getter
public enum StatusEnum {
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
