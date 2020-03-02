package com.tsf.demo.cmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;

@SpringBootApplication
@EnableDiscoveryClient // 使用服务注册发现时请启用
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableConfigurationProperties // 使用分布式配置时请启用
@EnableAutoConfiguration
@EnableTsfAuth
@EnableTsfRoute
@EnableTsfRateLimit
@EnableBinding({Source.class})
public class CmqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmqProducerApplication.class, args);
	}

}
