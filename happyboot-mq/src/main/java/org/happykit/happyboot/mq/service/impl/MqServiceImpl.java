package org.happykit.happyboot.mq.service.impl;

import org.happykit.happyboot.mq.model.Message;
import org.happykit.happyboot.mq.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MQ 消息服务
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/14
 */
@Slf4j
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendDirectMessage(String exchange, String routingKey, Message message) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (AmqpException e) {
            log.error("--- RabbitMQ 消息发送异常：" + e.getMessage());
        }
    }
}
