package com.tencent.tsf.gateway;

import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Kysonli
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTsfSleuth
@EnableTsfMonitor
public class GatewayServerApplication
{
    public static void main( String[] args )
    {
        SpringApplication springApplication = new SpringApplication(GatewayServerApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
