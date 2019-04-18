package com.gy.struggle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.gy.struggle.*.mapper"})
public class StruggleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StruggleApplication.class, args);
    }

}
