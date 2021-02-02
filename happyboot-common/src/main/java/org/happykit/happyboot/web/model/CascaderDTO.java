package org.happykit.happyboot.web.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 无限级联对象
 *
 * @author shaoqiang
 * @version 1.0 2020/6/9
 */
@Data
public class CascaderDTO implements Serializable {
    private String id;
    private String parentId;
    private String label;
    private List<CascaderDTO> children;
}