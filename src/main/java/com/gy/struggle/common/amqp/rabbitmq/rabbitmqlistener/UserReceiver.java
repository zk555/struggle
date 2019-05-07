package com.gy.struggle.common.amqp.rabbitmq.rabbitmqlistener;

import com.gy.struggle.common.domain.FileDO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "user")
public class UserReceiver {

    @RabbitHandler
    public void process(FileDO fileDO) {
        System.out.println("user receive  : " + fileDO.getId() + "/" + fileDO.getType());
    }
}