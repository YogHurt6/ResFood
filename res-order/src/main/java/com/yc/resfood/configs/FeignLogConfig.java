package com.yc.resfood.configs;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class FeignLogConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    //请求拦截器，统一加入origin请求头信息
    //feign中的拦截器机制也是一个责任链模式
    @Component
    public class CustomerRequestInterceptor implements RequestInterceptor{

        @Override
        public void apply(RequestTemplate requestTemplate) {
            //requestTemplate  请求对象
            requestTemplate.header("source","order-source");
        }
    }

}
