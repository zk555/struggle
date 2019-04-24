package com.gy.struggle.common.amqp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 发送消息方法
    public void send(String body) {

        kafkaTemplate.send("testTopic", body);
        logger.info("发送消息完成,内容 为:" + body);
    }
}
