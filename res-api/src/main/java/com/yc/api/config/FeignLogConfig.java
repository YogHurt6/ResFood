package com.yc.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignLogConfig {
    @Bean        //NONE,BASIC HEADERS,FULL,
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

}
