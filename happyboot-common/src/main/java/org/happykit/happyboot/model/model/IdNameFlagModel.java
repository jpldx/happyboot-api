package org.happykit.happyboot.model.model;

import lombok.Data;

/**
 * 通用id-name-flag对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
public class IdNameFlagModel {
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * 通用标识
     */
    private boolean flag;
}
