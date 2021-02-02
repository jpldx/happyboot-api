package org.happykit.happyboot.annotation;

import java.lang.annotation.*;

/**
 * 操作权限检查
 * 视角：
 * 外部视角：只能查询，不能操作
 * 对象视角：可以查询、操作
 * 部门视角：可以查询、操作
 *
 * @author chen.xudong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckPermission {
//    /** 必须性：must/必须 not_must/非必须 */
//    String type() default "must";
}
