package com.gy.struggle;

import com.gy.struggle.common.amqp.kafka.KafkaSender;
import com.gy.struggle.common.amqp.rabbitmq.HelloSender;
import com.gy.struggle.common.amqp.rabbitmq.TopicSender;
import com.gy.struggle.common.amqp.rabbitmq.UserSender;
import com.gy.struggle.common.controller.LockController;
import com.gy.struggle.common.utils.StringUtils;
import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StruggleApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    protected KafkaSender kafkaSender;

    @Autowired
    LockController lockController;

    @Test
    public void contextLoads() {
        String name = "1,2";
        List ids = new ArrayList();
        String[] split = name.split(",");
        if (split.length>0) {
            ids = Arrays.asList(split);
        }
        List<UserDO> userByName = userMapper.getUserByName(name,ids);
        System.out.println("userByName = " + userByName.get(0));
        System.out.println("userByName = " + userByName.size());
    }

    @Test
    public void testkafka(){
        kafkaSender.send("hello");
    }

    @Test
    public void redisTest() throws IOException {
        String name = lockController.query("name");
        System.out.println("name = " + name);
    }

    @Autowired
    private HelloSender helloSender1;
    @Autowired
    private UserSender userSender;

    @Autowired
    private TopicSender topicSender;
    @Test
    public void hello() {
        helloSender1.send();
    }

    @Test
    public void helloUser() {
        userSender.send();
    }
    @Test
    public void topicSender() {
        topicSender.send();
    }


}
