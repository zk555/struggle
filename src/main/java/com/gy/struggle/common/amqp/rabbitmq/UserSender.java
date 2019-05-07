package com.gy.struggle.common.amqp.rabbitmq;

import com.gy.struggle.common.domain.FileDO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        FileDO fileDO=new FileDO();
        fileDO.setType(1);
        fileDO.setId(500L);
        System.out.println("user send : " + fileDO.getId()+"/"+fileDO.getType());
        this.rabbitTemplate.convertAndSend("user", fileDO);
    }

}
