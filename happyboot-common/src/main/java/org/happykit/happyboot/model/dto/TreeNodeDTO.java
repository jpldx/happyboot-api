package org.happykit.happyboot.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 部门树
 *
 * @author chen.xudong
 * @date 2020/7/16
 */
@Data
public class TreeNodeDTO {
    private String id;
    private String parentId;
    private String title;
    private Boolean disabled;
    private Boolean leaf;
    private Boolean isLeadDept;
    private List<TreeNodeDTO> children;
}
