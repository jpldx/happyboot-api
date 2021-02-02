package org.happykit.happyboot.consumer.listener;

import com.alibaba.fastjson.JSON;
import org.happykit.happyboot.mq.constants.MqConstant;
import org.happykit.happyboot.mq.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 队列监听器
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/13
 */
@Slf4j
@Component
public class RabbitDirectListener {

    //    @RabbitHandler
    @RabbitListener(queues = MqConstant.MSG_QUEUE) // 监听的队列名称
    public void process(String payload) {
        log.info("--- MQ Consumer 接收消息成功：" + payload);
        Message msg = JSON.parseObject(payload, Message.class);
    }
}
