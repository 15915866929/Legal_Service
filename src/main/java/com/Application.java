package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 */
@SpringBootApplication
@ComponentScan("com")
@MapperScan("com.module.mapper")
@EnableScheduling
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
