package com.yc.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: yc119_cloud_res_parent
 * @description: 授权认证过滤器
 * @author: zy
 * @create: 2023-04-28 20:10
 */
//@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request= exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();
        String token=request.getHeaders().getFirst("token");
        log.info(   "AuthGlobalFilter请求来了，token值为:"+ token );
        Map map=new HashMap();
        if(StringUtils.isEmpty(    token ) ){
            map.put("code",0);
            map.put("msg","查无token");
        }else{
            //TODO:将来验证token
            if(  !"123".equals( token )){
                map.put("code",0);
                map.put("msg","token错误");
            }else{
                return chain.filter(   exchange );
            }
        }
        return doResponse(  response,  map );
    }

    private Mono<Void> doResponse(ServerHttpResponse response, Map map)  {
        response.getHeaders().add("Content-Type","application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json= objectMapper.writeValueAsString(    map );
            DataBuffer db=response.bufferFactory().wrap(    json.getBytes() );
            return response.writeWith(Flux.just(  db  ));
        } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
        }

    }

    @Override
    public int getOrder() {
        return  Ordered.HIGHEST_PRECEDENCE;
    }
}
