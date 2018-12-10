package com.syx.sboot.sbootStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/11/22 0022.
 */
@SpringBootApplication
@ComponentScan("com.syx.sboot")
@ServletComponentScan("com.syx.sboot")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.syx.sboot")
public class SyxSbootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyxSbootWebApplication.class);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
