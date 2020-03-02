package com.tsf.demo.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@EnableDiscoveryClient // 使用服务注册发现时请启用
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableScheduling
@EnableTsfAuth
@EnableTsfRoute
@EnableTsfRateLimit
@EnableTsfSleuth
@EnableTsfMonitor
public class MysqlDemoApplication {

    // 测试用户自定义DataSource时开启
	/*@Bean
	//@Primary  //在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() throws SQLException {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl("jdbc:mysql://127.0.0.1:3306/tsf_test?characterEncoding=utf8");
		datasource.setUsername("root");
		datasource.setPassword("123456");
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		return datasource;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(MysqlDemoApplication.class, args);
	}

}
