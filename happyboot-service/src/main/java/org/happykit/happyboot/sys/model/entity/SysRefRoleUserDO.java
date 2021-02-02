package org.happykit.happyboot.sys.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

/**
 * 角色与账号关系表
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@TableName("sys_ref_role_user")
public class SysRefRoleUserDO implements Serializable {
    /**
     * 主键id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 角色id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String roleId;
    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String userId;
    /**
     * 授权类型，01可见，02可授
     */
    private String authType;
}
