package com.tencent.tsf.msgw.zuul1;

import com.tencent.tsf.gateway.core.annotation.EnableTsfGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;

/**
 * @author seanlxliu
 * @since 2019/9/5
 */
@SpringBootApplication
@EnableTsfGateway
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsf
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
