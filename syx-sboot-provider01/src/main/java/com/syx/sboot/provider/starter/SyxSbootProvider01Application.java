package com.syx.sboot.provider.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by suyx on 2018/12/6 0006.
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.syx.sboot")
@EnableFeignClients("com.syx.sboot")
public class SyxSbootProvider01Application {
    public static void main(String[] args) {
        SpringApplication.run(SyxSbootProvider01Application.class);
    }
}
