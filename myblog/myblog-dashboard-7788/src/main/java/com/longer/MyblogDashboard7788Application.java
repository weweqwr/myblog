package com.longer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableEurekaClient//本服务启动后会自动注册进eureka服务
@EnableDiscoveryClient//服务发现
@EnableHystrixDashboard
public class MyblogDashboard7788Application {

    public static void main(String[] args) {
        SpringApplication.run(MyblogDashboard7788Application.class, args);
    }

}
