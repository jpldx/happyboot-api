package org.happykit.happyboot.log.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * <p>
 * TODO 审计日志注解， 保存请求信息
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/7/8
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * TODO 标题
     */
    String title();

    /**
     * TODO 操作信息
     */
    String operation() default StringUtils.EMPTY;
}
