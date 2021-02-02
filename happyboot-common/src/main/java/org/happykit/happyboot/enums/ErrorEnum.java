package org.happykit.happyboot.enums;

import lombok.Getter;

/**
 * 错误代码枚举
 *
 * @author shaoqiang
 * @version 1.0 2020/3/30
 */
@Getter
public class ErrorEnum {

    private Integer code;

    private String message;

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
