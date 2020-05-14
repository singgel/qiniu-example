package com.xueqiu.qiniu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xueqiu
 */
@SpringBootApplication
@PropertySource(value = "classpath:application.yml")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class).start();
    }
}
