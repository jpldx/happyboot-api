package org.happykit.happyboot.sys.enums;

import lombok.Getter;

/**
 * 模块枚举
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Getter
public enum ModuleEnum {
    /**
     * 后台菜单
     */
    ADMIN("admin", "后台菜单"),
    /**
     * 访问接口
     */
    API("api", "接口");

    private String code;

    private String message;

    ModuleEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
