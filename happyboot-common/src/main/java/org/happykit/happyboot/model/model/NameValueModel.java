package org.happykit.happyboot.model.model;

import lombok.Data;

/**
 * 通用name-value对象
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/21
 */
@Data
public class NameValueModel {
    /**
     * name
     */
    private String name;
    /**
     * value
     */
    private Object value;
}
