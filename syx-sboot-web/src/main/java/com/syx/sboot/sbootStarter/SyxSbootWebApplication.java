package com.syx.sboot.sbootStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2018/11/22 0022.
 */
@SpringBootApplication
@ComponentScan("com.syx.sboot")
public class SyxSbootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyxSbootWebApplication.class);
    }
}
