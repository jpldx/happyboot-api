package org.happykit.happyboot.sys.model.form;


import org.happykit.happyboot.constant.RegExpressionConstant;
import org.happykit.happyboot.util.NameUtils;
import org.happykit.happyboot.validation.Add;
import org.happykit.happyboot.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 用户账号表提交类
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
public class SysUserForm implements Serializable {
    @NotNull(message = "主键必须填", groups = Update.class)
    private String id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名必须填", groups = Add.class)
    @Pattern(regexp = NameUtils.regUsername, message = "登录账号不能包含特殊字符且长度不能>16")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码必须填", groups = Add.class)
    private String password;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码必须填", groups = Add.class)
    private String passwordConfirm;
//	/**
//	 * 账号类型 对象管理账号、对象内部账号、主管部门账号(如果是对象管理账号、对象内部账号，角色只能选择单位角色)
//	 */
    /**
     * 账号类型（0/主账号 1/子账号）
     */
    @NotBlank(message = "账号类型必须填")
    private String userType;
    /**
     * 关联的账号列表
     */
    private List<String> userIdList;
    /**
     * 昵称
     */
    @NotBlank(message = "昵称必须填", groups = Add.class)
    @Size(max = 20, message = "昵称长度不能超过20")
    private String nickname;
    /**
     * 手机号码
     */
    @Pattern(regexp = RegExpressionConstant.REG_MOBILE, message = "手机号码格式错误")
    private String phoneNumber;
    /**
     * 头像
     */
    private String headPic;
    /**
     * 0=禁用 1=启用
     */
    @NotNull(message = "状态必须填")
    private Integer status;
    /**
     * 所选部门
     */
    private List<Long> deptIds;
    /**
     * 所选区域
     */
    private List<Long> regionIds;


}