package com.longer;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient//本服务启动后会自动注册进eureka服务
@EnableDiscoveryClient//服务发现
@MapperScan(value = "com.longer.mapperDao")
@EnableCircuitBreaker
public class MyblogUserProvider8002Application {

    public static void main(String[] args) {
        SpringApplication.run(MyblogUserProvider8002Application.class, args);
    }

}
