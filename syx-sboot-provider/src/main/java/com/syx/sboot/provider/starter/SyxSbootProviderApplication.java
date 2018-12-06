package com.syx.sboot.provider.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by suyx on 2018/12/6 0006.
 */
@SpringBootApplication
@EnableEurekaClient
public class SyxSbootProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SyxSbootProviderApplication.class);
    }
}
