package org.happykit.happyboot.sys.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 区域部门表分页查询对象
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysDeptRegionQueryParam implements Serializable {
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 区域编号
     */
    private String regionCode;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
}
