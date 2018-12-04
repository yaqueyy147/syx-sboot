package com.syx.sboot.webback.sbootStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2018/11/22 0022.
 */
@SpringBootApplication
@ComponentScan("com.syx.sboot")
@ServletComponentScan("com.syx.sboot")
public class SyxSbootWebBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyxSbootWebBackApplication.class);
    }
}
