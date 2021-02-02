package org.happykit.happyboot.base;

/**
 * <p>
 * 通用枚举
 * </p>
 *
 * @author shaoqiang
 * @version 1.0 2020/7/8
 */
public interface IEnum<K, V> {
    /**
     * TODO 枚举值
     *
     * @return
     */
    public K getCode();

    /**
     * TODO 枚举描述
     *
     * @return
     */
    public V getMessage();
}
