package org.happykit.happyboot.enums;

import lombok.Getter;

/**
 * 用户状态
 *
 * @author shaoqiang
 * @version 1.0 2020/6/17
 */
@Getter
public enum UserStatusEnum {
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private Integer code;

    private String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
