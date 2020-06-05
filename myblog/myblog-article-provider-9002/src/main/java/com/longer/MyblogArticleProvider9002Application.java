package com.longer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient//本服务启动后会自动注册进eureka服务
@EnableDiscoveryClient//服务发现
@MapperScan(value = "com.longer.mapperDao")
@EnableCircuitBreaker
public class MyblogArticleProvider9002Application {

    public static void main(String[] args) {
        SpringApplication.run(MyblogArticleProvider9002Application.class, args);
    }

}
