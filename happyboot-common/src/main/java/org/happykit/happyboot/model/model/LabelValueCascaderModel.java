package org.happykit.happyboot.model.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 无线级联对象
 *
 * @author chen.xudong
 * @version 1.0 2020/7/22
 */
@Data
public class LabelValueCascaderModel implements Serializable {
    private String label;
    private String value;
    private List<LabelValueCascaderModel> options;
}