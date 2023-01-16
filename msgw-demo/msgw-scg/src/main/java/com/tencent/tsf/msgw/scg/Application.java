package com.tencent.tsf.msgw.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.tsf.annotation.EnableTsf;
/**
 * @author seanlxliu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTsf
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
