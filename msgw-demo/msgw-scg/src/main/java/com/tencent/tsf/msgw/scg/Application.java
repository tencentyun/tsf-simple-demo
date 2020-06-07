package com.tencent.tsf.msgw.scg;

import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author seanlxliu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTsfSleuth
@EnableTsfMonitor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
