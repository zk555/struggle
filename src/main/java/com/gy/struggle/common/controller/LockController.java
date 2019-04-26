package com.gy.struggle.common.controller;

import com.gy.struggle.common.annotation.RedisLock;
import com.gy.struggle.common.annotation.RedisParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockController {

    @RedisLock(prefix = "controller",expire = 50)
    public String query(@RedisParam(name = "token") @RequestParam String token) {
             return "success - " + token;
     }

}
