package com.tencent.tsf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Kysonli
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication
{
    public static void main( String[] args )
    {
        SpringApplication springApplication = new SpringApplication(GatewayServerApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
