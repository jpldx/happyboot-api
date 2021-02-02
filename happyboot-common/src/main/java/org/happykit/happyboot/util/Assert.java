package org.happykit.happyboot.util;

import org.happykit.happyboot.constant.SysExceptionConstant;
import org.happykit.happyboot.exception.SysException;

/**
 * 断言工具
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/10/19
 */
public class Assert {

    /**
     * 判断是否未找到相关记录
     *
     * @param obj 断言对象
     */
    public static void isNotFound(Object obj) {
        if (null == obj) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_RECORD);
        }
    }

    /**
     * 判断是否未找到相关用户
     *
     * @param obj 断言对象
     */
    public static void isNotFoundUser(Object obj) {
        if (null == obj) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_USER);
        }
    }

    /**
     * 判断是否未找到相关部门
     *
     * @param obj 断言对象
     */
    public static void isNotFoundDept(Object obj) {
        if (null == obj) {
            throw new SysException(SysExceptionConstant.NOT_FOUND_DEPT);
        }
    }

    /**
     * 判断是否未找到
     *
     * @param obj 断言对象
     */
    public static void isNotFound(Object obj, String msg) {
        if (null == obj) {
            throw new SysException(msg);
        }
    }

    /**
     * 判断是否为空字符串
     *
     * @param str
     * @param msg
     */
    public static void isBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new SysException(msg);
        }
    }


}
