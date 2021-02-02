package org.happykit.happyboot.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * ID生成器
 *
 * @author chen.xudong
 * @date 2020/6/29
 */
public class IdGenerator {
    /**
     * 生成分布式唯一ID（雪花算法）
     *
     * @return Long
     */
    public static Long generateId() {
        return IdWorker.getId();
    }

    /**
     * 生成分布式唯一ID（雪花算法）
     *
     * @return String
     */
    public static String generateIdStr() {
        return IdWorker.getIdStr();
    }

    public static void main(String[] args) {
        int num = 10;
        for (int i = 0; i < num; i++) {
            System.out.println(generateIdStr());
        }
    }
}
