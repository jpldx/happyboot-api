package org.happykit.happyboot.common.service.openapi.baidu.map.service;

/**
 * 位置服务
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
public interface ILocationService {

    /**
     * 根据IP获取地址
     *
     * @param ip
     * @return
     */
    String getAddressByIp(String ip);
}
