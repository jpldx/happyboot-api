package org.happykit.happyboot.mq.constants;

/**
 * MQ 常量
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/14
 */
public interface MqConstant {

    String MSG_EXCHANGE = "happyboot.msg.direct.exchange";

    /**
     * 系统消息队列
     */
    String MSG_QUEUE = "happyboot-msg-queue";
    String MSG_ROUTING_KEY = "happpyboot-msg-routingkey";


    /**
     * 用户登录日志队列
     */
    String LOGIN_LOG_QUEUE = "happyboot-login-log-queue";
    String LOGIN_LOG_ROUTING_KEY = "happyboot-login-log-routingkey";
}
