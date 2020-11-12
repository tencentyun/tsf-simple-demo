package com.tencent.tsf.msgw.zuul1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;

/**
 * @author seanlxliu
 * @since 2019/9/5
 */
@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsf
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
