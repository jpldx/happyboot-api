package org.happykit.happyboot.mq.config;

import org.happykit.happyboot.mq.constants.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Direct-Exchange Configuration
 *
 * @author chen.xudong
 * @version 1.0
 * @since 2021/1/12
 */
@Configuration
public class RabbitDirectConfig {

    // 队列 名称：test-direct-queue
    @Bean
    public Queue msgDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(MqConstant.MSG_QUEUE, true);
    }

    // Direct交换机 名称：test-direct-exchange
    @Bean
    DirectExchange msgDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange(MqConstant.MSG_EXCHANGE, true, false);
    }

    //绑定 将队列和交换机绑定, 并设置用于匹配键：test-direct-routing-key
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(msgDirectQueue()).to(msgDirectExchange()).with(MqConstant.MSG_ROUTING_KEY);
    }

//    @Bean
//    DirectExchange lonelyDirectExchange() {
//        return new DirectExchange("lonelyDirectExchange");
//    }
}
