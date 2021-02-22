package org.happykit.happyboot.log.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2020/3/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志类型
     *
     * @return
     */
    LogType type() default LogType.SYS;

    /**
     * 描述信息
     *
     * @return
     */
    String des() default "";
}
