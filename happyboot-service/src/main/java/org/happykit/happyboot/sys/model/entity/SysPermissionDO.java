package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台操作权限
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/3/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermissionDO extends BaseEntity {
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
     * 类型，菜单还是按钮,url
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

    @TableField(value = "is_router")
    private Boolean router;
    /**
     * 是否缓存
     */
    @TableField(value = "is_keepalive")
    private Boolean keepalive;
    /**
     * 是否外链
     */
    @TableField(value = "is_external_link")
    private Boolean externalLink;
    /**
     * 外链地址
     */
    private String externalLinkAddress;
    /**
     * 是否隐藏路由
     */
    @TableField(value = "is_hide")
    private Boolean hide;
    /**
     * 是否首页
     */
    @TableField(value = "is_home")
    private Boolean home;
    /**
     * 按钮标识
     */
    private String permissionKey;
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
     * 所属模块，admin后台，api
     */
    private String module;
}
