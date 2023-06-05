package com.yc.resfood.configs;

import com.yc.resfood.mybalancer.MyLoadBalancerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableDiscoveryClient
//给这个服务res-food指定负载均衡策略，默认有两个策略：轮询和随机
//LoadBalancerClientConfiguration类是系统提供
//@LoadBalancerClient(name = "res-food",configuration = LoadBalancerClientConfiguration.class)
//使用自定义的负载均衡
//@LoadBalancerClient(name = "res-food",configuration = MyLoadBalancerConfig.class)
//对多个服务绑定不同的负载均衡
@LoadBalancerClients(
        value = {
                @LoadBalancerClient(name = "res-food",configuration = MyLoadBalancerConfig.class),//绑定负载均衡时，使用默认与自定义冲突导致错误
                @LoadBalancerClient(name = "res-order",configuration = MyLoadBalancerConfig.class)
        },defaultConfiguration = LoadBalancerClientConfiguration.class
)
public class ApplicationConfig {
    @Bean
    @LoadBalanced //负载平衡器： 一个服务器下有多个服务节点
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //LoadBalance特有  组件LoadBalance  nacos   feign.. webclient
    //                ribbon       eurake      feign
//    @Bean
//    @LoadBalanced
//    public WebClient webClient(){
//        String baseUrl = "https: //example.com" ;
//        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl)
//        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TENPLATE_AND_VALUES);
//        //customize the Webclient. .
//        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
//        return client;
//    }
}
