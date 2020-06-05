package com.longer;

import com.longer.config.FeignHystrixConcurrencyStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.longer"})
public class MyblogArticleConsumer5001Application {

    public static void main(String[] args) {
        SpringApplication.run(MyblogArticleConsumer5001Application.class, args);
    }

    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }

}
