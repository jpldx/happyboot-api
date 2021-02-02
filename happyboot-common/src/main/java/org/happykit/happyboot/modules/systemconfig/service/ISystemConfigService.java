package org.happykit.happyboot.modules.systemconfig.service;

/**
 * 系统配置
 *
 * @author chen.xudong
 * @date 2020/8/8
 */
public interface ISystemConfigService {
    /**
     * 通过key获取value
     *
     * @param key 键
     * @return
     */
    String getValueByKey(String key);
}
