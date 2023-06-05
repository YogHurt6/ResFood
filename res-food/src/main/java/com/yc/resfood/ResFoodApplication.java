package com.yc.resfood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.yc.resfood.dao")
@EnableDiscoveryClient//启动服务注册发现的客户端
public class ResFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResFoodApplication.class, args);

    }

}
