package com.tencent.tsf.demo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;

@SpringCloudApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
@ConfigurationProperties
@EnableTsfAuth
@EnableTsfRoute
@EnableTsfRateLimit
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
