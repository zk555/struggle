package com.gy.struggle.common.amqp.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * class_name: FanoutSender
 * package: com.gy.struggle.common.amqp.rabbitmq
 * describe: TODO ：广播模式
 * creat_user: zhaokai@
 * creat_date: 2019/5/7
 * creat_time: 15:35
 **/
public class FanoutSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String msgString="fanoutSender :hello i am hzb";
        System.out.println(msgString);
        this.rabbitTemplate.convertAndSend("fanoutExchange","abcd.ee", msgString);
    }

}
