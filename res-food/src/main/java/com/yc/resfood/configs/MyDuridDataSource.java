package com.yc.resfood.configs;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//将原来的自动IOC的druidDataSource方案改为手工方式
@Configuration

@Slf4j
@RefreshScope
public class MyDuridDataSource {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Bean
    @Primary//优先IOC  会有多个数据源
    @RefreshScope
    public DataSource druid(){
        log.info("使用的编程式的数据源创建");
        DruidDataSource druidDataSource=new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(this.driverClassName);
        return druidDataSource;
    }
}
