package org.happykit.happyboot.sys.enums;


import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author shaoqiang
 * @version 1.0 2020/6/18
 */
@Getter
public enum AuthTypeEnum {
    /**
     * 权限可见
     */
    VISIBLE("01", "权限可见"),
    /**
     * 权限可授
     */
    GRANTED("02", "权限可授");

    private String code;

    private String message;

    AuthTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
