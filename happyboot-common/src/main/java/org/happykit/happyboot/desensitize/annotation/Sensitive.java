package org.happykit.happyboot.desensitize.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.happykit.happyboot.desensitize.enums.SensitiveTypeEnum;
import org.happykit.happyboot.desensitize.serialize.SensitiveSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏
 *
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface Sensitive {
    /**
     * 脱敏类型(规则)
     *
     * @return
     */
    SensitiveTypeEnum value();
}
