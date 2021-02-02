package org.happykit.happyboot.log.annotation;

import java.lang.annotation.*;

/**
 * web日志注解
 *
 * @author shaoqiang
 * @version 1.0 2020/3/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    /**
     * 日志描述信息
     *
     * @return
     */
    String description() default "";
}
