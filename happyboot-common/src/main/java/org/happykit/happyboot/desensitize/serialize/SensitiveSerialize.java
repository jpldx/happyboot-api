package org.happykit.happyboot.desensitize.serialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.happykit.happyboot.desensitize.annotation.Sensitive;
import org.happykit.happyboot.desensitize.enums.SensitiveTypeEnum;
import org.happykit.happyboot.desensitize.util.SensitiveUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏
 *
 * @author shaoqiang
 * @version 1.0 2020/3/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {
    private SensitiveTypeEnum type;

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property) throws JsonMappingException {
        // 为空直接跳过
        if (property != null) {
            // 非String类直接跳过
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                Sensitive sensitive = property.getAnnotation(Sensitive.class);
                if (sensitive == null) {
                    sensitive = property.getContextAnnotation(Sensitive.class);
                }
                // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
                if (sensitive != null) {
                    return new SensitiveSerialize(sensitive.value());
                }
            }
            return serializerProvider.findValueSerializer(property.getType(), property);
        } else {
            return serializerProvider.findNullValueSerializer(null);
        }
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        switch (this.type) {
            case MOBILE:
                gen.writeString(SensitiveUtils.mobile(value));
                break;
            case EMAIL:
                gen.writeString(SensitiveUtils.email(value));
                break;
            case CHINESE_NAME:
                gen.writeString(SensitiveUtils.chineseName(value));
                break;
            case ID_CARD:
                gen.writeString(SensitiveUtils.idCard(value));
                break;
            case PASSWORD:
                gen.writeString(SensitiveUtils.password());
                break;
            case BANK_CARD:
                gen.writeString(SensitiveUtils.bankCard(value));
            default:
                gen.writeString(SensitiveUtils.COMMON);
                break;
        }
    }
}
