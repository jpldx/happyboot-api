package org.happykit.happyboot.mybatisplus.annotation;


import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author shaoqiang
 * @version 1.0 2020/3/16
 */
@Documented
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataAuth {
    /**
     * 是否启用用户所在部门组织所看的单位数据权限
     *
     * @return
     */
    boolean enableDept() default false;

    /**
     * 是否启用用户所在的单位数据权限
     *
     * @return
     */
    boolean enableObj() default false;

    /**
     * 是否启用用户关联区域所看的单位数据的数据权限
     *
     * @return
     */
    boolean enableRegion() default false;
}
