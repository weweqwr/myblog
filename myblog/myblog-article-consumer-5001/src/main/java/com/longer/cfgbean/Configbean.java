package com.longer.cfgbean;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configbean {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTenplate(){
        return new RestTemplate();
    }
}
