package com.gy.struggle.common.controller;

import com.gy.struggle.common.annotation.CacheLock;
import com.gy.struggle.common.annotation.CacheParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockController {

    @CacheLock(prefix = "controller")
    public String query(@CacheParam(name = "token") @RequestParam String token) {
             return "success - " + token;
     }

}
