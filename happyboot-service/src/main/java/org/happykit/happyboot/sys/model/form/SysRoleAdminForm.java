package org.happykit.happyboot.sys.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author shaoqiang
 * @version 1.0 2020/3/27
 */
@Data
public class SysRoleAdminForm implements Serializable {
    /**
     * 角色id
     */
    @NotNull(message = "角色必须填")
    private Long roleId;
    /**
     * 权限集合
     */
    private List<Long> adminIds;
}
