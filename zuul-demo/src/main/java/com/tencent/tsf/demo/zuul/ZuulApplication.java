package com.tencent.tsf.demo.zuul;

import com.tencent.tsf.demo.zuul.filter.TestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.tsf.annotation.EnableTsf;

@SpringCloudApplication
@EnableZuulProxy
@EnableFeignClients
@EnableTsf
public class ZuulApplication {

    @Bean
    public TestFilter testFilter() {
        return new TestFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
