package com.gy.struggle;

import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StruggleApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        String name = "1";
        List<UserDO> userByName = userMapper.getUserByName(name,name);
        System.out.println("userByName = " + userByName.get(0));
        System.out.println("userByName = " + userByName.size());
    }

}
