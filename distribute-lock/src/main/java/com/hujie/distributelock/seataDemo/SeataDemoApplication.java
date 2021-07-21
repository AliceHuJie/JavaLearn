package com.hujie.distributelock.seataDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication()
@EnableFeignClients
@Configuration
@MapperScan(basePackages = {"com.hujie.distributelock.seataDemo"})
public class SeataDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataDemoApplication.class, args);
    }

}
