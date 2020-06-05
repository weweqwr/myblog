package com.longer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MyblogConfig3344Application {

    public static void main(String[] args) {
        SpringApplication.run(MyblogConfig3344Application.class, args);
    }

}
