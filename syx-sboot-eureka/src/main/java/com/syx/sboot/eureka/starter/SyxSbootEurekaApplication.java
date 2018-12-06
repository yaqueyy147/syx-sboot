package com.syx.sboot.eureka.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by suyx on 2018/12/6 0006.
 */
@EnableEurekaServer
@SpringBootApplication
public class SyxSbootEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyxSbootEurekaApplication.class);
    }

}
