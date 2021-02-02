package org.happykit.happyboot.sys.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.happykit.happyboot.validation.Add;
import org.happykit.happyboot.validation.Update;

import lombok.Data;

/**
 * 前端菜单提交对象
 *
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Data
public class SysMenuForm implements Serializable {
    /**
     * 主键
     */
    @NotNull(message = "主键必须填", groups = Update.class)
    @Null(message = "主键必须为空", groups = Add.class)
    private Long id;
    /**
     * 父id
     */
    @NotNull(message = "父ID必须填", groups = {Add.class, Update.class})
    private Long parentId;
    /**
     * 名称
     */
    @NotBlank(message = "名称必须填", groups = {Add.class, Update.class})
    private String name;
    /**
     * 类型，菜单还是按钮
     */
    @NotBlank(message = "类型必须填", groups = {Add.class, Update.class})
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
    private String externalLink;
    /**
     * 是否首页
     */
    private Boolean home;
    /**
     * 外链地址
     */
    private String externalLinkAddress;
    /**
     * 是否隐藏路由
     */
    private Boolean hide;
    /**
     * 按钮标识
     */
    private String permissionKey;
    /**
     * 浏览器标签 _self _blank
     */
    private String linkTarget;
    /**
     * 0=禁用 1=启用
     */
    @NotNull(message = "状态必须填", groups = {Add.class, Update.class})
    private Integer status;
    /**
     * 排序
     */
    @NotNull(message = "排序号必须填", groups = {Add.class, Update.class})
    private Integer orderId;
}
