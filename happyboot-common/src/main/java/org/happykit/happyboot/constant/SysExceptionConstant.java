package org.happykit.happyboot.constant;

/**
 * 系统异常信息
 *
 * @author chen.xudong
 * @date 2020/6/16
 * @see org.happykit.happyboot.exception.SysException
 */
public interface SysExceptionConstant {
    /**
     * 未找到异常
     */
    String NOT_FOUND_USER = "用户信息不存在";
    String NOT_FOUND_DEPT = "部门信息不存在";
    String NOT_FOUND_RECORD = "未找到相关记录";
    String NOT_FOUND_CONFIG = "未找到相关配置项";
    /**
     * 解析异常
     */
    String ANALYSIS_ERROR_LONG_LAT = "经纬度解析异常";
    /**
     * 不正确异常
     */
    String WRONG_OLD_PASSWORD = "原密码不正确";
    String WRONG_CONFIRM_PASSWORD = "两次输入的密码不一致";
    /**
     * 权限异常
     */
    String AUTH_ERROR = "无权限操作";
    String AUTH_ERROR_DEPT = "部门视角未切换，无权限操作";
    String AUTH_NOT_BIND_DEPT = "部门未绑定，无权限操作";
    /**
     * 参数异常
     */
    String PARAM_ERROR = "非法传参";
    /**
     * 不支持异常
     */
    String UNSUPPORTED_PARAM_TYPE = "不支持的参数类型";
}
