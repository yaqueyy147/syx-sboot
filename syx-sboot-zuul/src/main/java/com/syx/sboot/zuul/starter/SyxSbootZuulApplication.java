package com.syx.sboot.zuul.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2018/12/7 0007.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@ComponentScan("com.syx.sboot")
public class SyxSbootZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyxSbootZuulApplication.class);
    }

}
