package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 对象与对象类型关系表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysRefObjTypeForm implements Serializable {
    /**
     * 单位id
     */
    private Long objId;
    /**
     * 类型
     */
    private Integer objType;
}