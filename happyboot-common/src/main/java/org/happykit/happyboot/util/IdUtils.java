package org.happykit.happyboot.util;

import java.util.UUID;

/**
 * ID生成器
 *
 * @author shaoqiang
 * @version 1.0 2020/6/8
 */
public class IdUtils {

    /**
     * 获取uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取去除-的uuid
     *
     * @return
     */
    public static String simpleUUID() {
        return uuid().replaceAll("-", "");
    }

}

