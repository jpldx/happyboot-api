package org.happykit.happyboot.sys.model.query;


import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询对象
 *
 * @author shaoqiang
 * @version 1.0 2020/3/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageQueryParam extends PageQuery {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色代码
     */
    private String authorityName;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;
}
