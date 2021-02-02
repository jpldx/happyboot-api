package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.happykit.happyboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户账号关联表
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/12/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_rel")
public class SysUserRelDO extends BaseEntity {
    /**
     * 主账号id
     */
    private String mainUserId;
    /**
     * 子账号id
     */
    private String subUserId;

    public SysUserRelDO() {
    }

    public SysUserRelDO(String mainUserId, String subUserId) {
        this.mainUserId = mainUserId;
        this.subUserId = subUserId;
    }
}
