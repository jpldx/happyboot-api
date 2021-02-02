package org.happykit.happyboot.mq.service;

import org.happykit.happyboot.mq.model.Message;

/**
 * MQ 消息服务
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/14
 */
public interface MqService {

    /**
     * 发送直连消息
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息内容
     */
    void sendDirectMessage(String exchange, String routingKey, Message message);
}
