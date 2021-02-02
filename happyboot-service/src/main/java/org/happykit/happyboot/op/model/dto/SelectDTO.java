package org.happykit.happyboot.op.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Select数据格式
 *
 * @author shaoqiang
 * @version 1.0 2020/5/16
 */
@Data
public class SelectDTO implements Serializable {
    private String label;
    private String value;
}
