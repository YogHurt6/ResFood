package com.yc.resfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(backPackages ={"com.yc"})
@EnableDiscoveryClient
public class ResIndexApp {
    public static void main(String[] args) {
        SpringApplication.run(ResIndexApp.class,args);
    }

}
//http://localhost:6666/index.html  ->直接指定资源为静态资源
//http://localhost:6666/    ->动态的controller