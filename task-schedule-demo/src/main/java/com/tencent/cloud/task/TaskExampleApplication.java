package com.tencent.cloud.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TaskExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskExampleApplication.class, args);
    }
}