package com.gy.struggle.common.amqp.rabbitmq.rabbitmqlistener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
    @RabbitHandler
    public void process(String hello) {
        //  这里需要指定发送的消息队列
        System.out.println("Receiver1  : " + hello);
    }

}
