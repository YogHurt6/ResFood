package com.yc.resfood.mybalancer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.net.ServerSocket;
import java.util.List;

public class MyOnlyOnceLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    //服务列表
    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public MyOnlyOnceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier=this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);//lamda

        return supplier.get(request).next().map((serviceInstances)->{
            return processInstanceResponse(supplier,serviceInstances);
        });
    }

    //获取服务实例
    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback)supplier).selectedServiceInstance((ServiceInstance)serviceInstanceResponse.getServer());
        }

        return serviceInstanceResponse;
    }
    //int i=0;  //多线程
    //Map<ServiceInstance,Long>map;
    //从实力列表中取第一个实例
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstances) {
        if (serviceInstances.isEmpty()){
            return new EmptyResponse();
        }
        //取第一个
        ServiceInstance si=serviceInstances.get(0);
        //roundrobin:  ->  i++
        //random  ->  random.nextInt(serviceInstance.size())
        //按流量的权重:  ->  记录每个serviceInstance的访问数量 ，取倒数
        return new DefaultResponse(si);
    }

}
