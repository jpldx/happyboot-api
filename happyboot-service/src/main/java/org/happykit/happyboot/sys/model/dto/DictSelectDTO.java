package org.happykit.happyboot.sys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Select数据格式
 *
 * @author shaoqiang
 * @version 1.0 2020/5/16
 */
@Data
@AllArgsConstructor
public class DictSelectDTO implements Serializable {
    private String label;
    private String value;
}
