package org.happykit.happyboot.util;

import org.happykit.happyboot.enums.StatusEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shaoqiang
 * @version 1.0 2020/6/28
 */
public class EnumUtils {
    /**
     * 使用枚举类型对应的typeName获取枚举类型
     *
     * @param clazz                 枚举类的class
     * @param getTypeNameMethodName 传入的typeName的get方法
     * @param typeName              传入的typeName值，这个方法为Object类型
     */
    public static <T extends Enum<T>> T getEnumsValue(Class<T> clazz, String getTypeNameMethodName, Object typeName) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getTypeNameMethodName);
            Object typeNameVal = null;
            for (T entity : arr) {
                typeNameVal = targetMethod.invoke(entity);
                if (typeNameVal.equals(typeName)) {
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        StatusEnum byStringTypeName = EnumUtils.getEnumsValue(StatusEnum.class, "getCode", 1);
        System.out.println(byStringTypeName.getCode());
    }
}
