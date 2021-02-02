package org.happykit.happyboot.web.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树状节点
 *
 * @author shaoqiang
 * @version 1.0 2020/6/19
 */
@Data
public class TreeNodeDTO implements Serializable {
    private String id;
    private String parentId;
    private String title;
    private Boolean disabled;
    private Boolean leaf;
    private List<TreeNodeDTO> children;
}
