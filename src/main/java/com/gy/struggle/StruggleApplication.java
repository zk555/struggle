package com.gy.struggle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ServletComponentScan
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.gy.struggle.*.mapper"})
public class StruggleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StruggleApplication.class, args);
    }

}
