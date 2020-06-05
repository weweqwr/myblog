package com.longer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblogApi1001Application {
    public static void main(String[] args) {
        SpringApplication.run(MyblogApi1001Application.class, args);
    }
}
