package org.happykit.happyboot.sys.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.happykit.happyboot.base.BaseEntity;
import org.happykit.happyboot.util.NameUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * 用户账号表
 *
 * @author shaoqiang
 * @version 1.0 2020/04/01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUserDO extends BaseEntity {
//	private interface WithPwdView extends View.SimpleView {}
	/**
	 * 登录名
	 */
	@Pattern(regexp = NameUtils.regUsername, message = "登录账号不能包含特殊字符且长度不能>16")
	private String username;
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * md5密码盐
	 */
	private String salt;
	/**
	 * 密码
	 */
//	@JsonView(value = WithPwdView.class)
	private String password;
//	/**
//	 * 账号类型 对象管理账号、对象内部账号、主管部门账号(如果是对象管理账号、对象内部账号，角色只能选择单位角色)
//	 */
	/**
	 * 账号类型（0/主账号 1/子账号）
	 */
	private String userType;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像
	 */
	private String headPic;
	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime lastLoginTime;
	/**
	 * 最后登录ip
	 */
	private String lastIp;
	/**
	 * 0=禁用 1=启用
	 */
	private Integer status;
	/**
	 * 主体id
	 */
	private String subjectId;
	/**
	 * 部门id
	 */
	private String deptId;
}
