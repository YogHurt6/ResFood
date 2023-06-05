package com.yc.resfood.mybalancer;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@ConditionalOnProperty
public class MyLoadBalancerConfig {

//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanLoadBalancer(Environment environment,
//                                                                                 LoadBalancerClientFactory loadBalancerClientFactory){
//        //name指定的是服务名，这样这个服务就和一个负载均衡器绑定
//        String name=environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new MyOnlyOnceLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class)
//        );
//    }

//    @Bean
//    //@ConditionalOnMissingBean
//    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanLoadBalancer(Environment environment,
//                                                                                 LoadBalancerClientFactory loadBalancerClientFactory){
//        //res-food服务名
//        String name=environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RoundRobinLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),name
//        );
//    }

    @Bean
    //@ConditionalOnMissingBean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanLoadBalancer(Environment environment,
                                                                                 LoadBalancerClientFactory loadBalancerClientFactory){
        //res-food服务名
        String name=environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),name
        );
    }
}
