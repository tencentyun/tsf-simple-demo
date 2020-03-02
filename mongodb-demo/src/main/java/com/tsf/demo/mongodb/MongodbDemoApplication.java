package com.tsf.demo.mongodb;

import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;

@SpringBootApplication
@EnableDiscoveryClient // 使用服务注册发现时请启用
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableScheduling
@EnableTsfAuth
@EnableTsfRoute
@EnableTsfRateLimit
@EnableTsfSleuth
@EnableTsfMonitor
public class MongodbDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbDemoApplication.class, args);
    }

}
