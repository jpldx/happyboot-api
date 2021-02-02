package org.happykit.happyboot.sys.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树
 *
 * @author shaoqiang
 * @version 1.0 2020/3/12
 */
@Data
public class SysPermissionTreeDTO implements Serializable {
    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    /**
     * 父id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型，菜单还是按钮
     */
    private String type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 路径
     */
    private String path;
    /**
     * 视图容器
     */
    private String view;
    /**
     * 是否路由
     */
    private Boolean router;
    /**
     * 是否缓存
     */
    private Boolean keepalive;
    /**
     * 是否外链
     */
    private Boolean externalLink;
    /**
     * 外链地址
     */
    private String externalLinkAddress;
    /**
     * 按钮标识
     */
    private String permissionKey;
    /**
     * 是否隐藏路由
     */
    private Boolean hide;
    /**
     * 是否首页
     */
    private Boolean home;
    /**
     * 浏览器标签 _self _blank
     */
    private String linkTarget;
    /**
     * 排序,数字越小排越靠前
     */
    private Integer orderId;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
    /**
     * 子集
     */
    private List<SysPermissionTreeDTO> children;
}
