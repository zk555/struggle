package com.gy.struggle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"com.gy.struggle.*.mapper"})
public class StruggleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StruggleApplication.class, args);
    }

}
