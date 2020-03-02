package com.tsf.demo.cmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
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
@EnableBinding(Processor.class)
public class CmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmqConsumerApplication.class, args);
	}

}
