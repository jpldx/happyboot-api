package org.happykit.happyboot.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 下拉框
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Data
@AllArgsConstructor
public class SelectDTO implements Serializable {
    private String label;
    private String value;
}
