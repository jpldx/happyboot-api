package org.happykit.happyboot.model.model;

import lombok.Data;

/**
 * 通用label-value对象
 *
 * @author chen.xudong
 * @date 2020/4/14
 */
@Data
public class LabelValueModel {
    /**
     * label
     */
    private String label;
    /**
     * value
     */
    private Object value;
}
