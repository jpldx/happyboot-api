package org.happykit.happyboot.sys.model.query;


import org.happykit.happyboot.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户账号表分页查询对象
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPageQueryParam extends PageQuery {
    /**
     * 账号或者昵称
     */
    private String account;
    /**
     * 0=禁用 1=启用
     */
    private Integer status;

    /**
     * 账号类型（0/主账号 1/子账号）
     */
    private String userType;
}
