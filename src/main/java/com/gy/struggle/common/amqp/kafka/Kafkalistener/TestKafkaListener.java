package com.gy.struggle.common.amqp.kafka.Kafkalistener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

import java.lang.annotation.Annotation;

@KafkaListener(topics = { "testTopic" })
public class TestKafkaListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("接收消息为:" + record.value());
    }


}
