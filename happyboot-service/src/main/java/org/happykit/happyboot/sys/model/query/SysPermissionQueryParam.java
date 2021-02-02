package org.happykit.happyboot.sys.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/16
 */
@Data
public class SysPermissionQueryParam implements Serializable {
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
}
