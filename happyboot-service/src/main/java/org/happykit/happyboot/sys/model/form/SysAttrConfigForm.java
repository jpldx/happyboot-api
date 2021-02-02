package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交类
 *
 * @author cly
 * @version 1.0 2020/07/06
 */
@Data
public class SysAttrConfigForm implements Serializable {
    /**
     *
     */
    private String key;
    /**
     *
     */
    private String value;
}