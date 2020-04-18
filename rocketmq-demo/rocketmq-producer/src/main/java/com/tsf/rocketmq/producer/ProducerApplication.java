package com.tsf.rocketmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;

@SpringBootApplication
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsf
public class ProducerApplication {

    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class, args);
    }


}
