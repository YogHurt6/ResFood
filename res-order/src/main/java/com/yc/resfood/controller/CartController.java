package com.yc.resfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.bean.Resfood;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("cart")
public class CartController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ResfoodApi resfoodApi;

    @RequestMapping("clearAll")
    public Map<String,Object> clearAll(@RequestHeader    String token){
        Map<String,Object> result=new HashMap<>();
        //String sessionid=session.getId();
        if(  this.redisTemplate.hasKey("cart_"+token)){
            Set<Object> keys= this.redisTemplate.opsForHash().keys(  "cart_"+token);
            // keys: 1,2  =>     "1,2"
            this.redisTemplate.opsForHash().delete(   "cart_"+token,  keys.toArray()  );
            result.put("code",   1   );
            result.put("obj",keys);   //keys中存的是 删除的商品编号
        }else{
            result.put("code",0);
        }
        return result;
    }

    @RequestMapping("getCartInfo")
    public Map<String,Object> getCartInfo(@RequestHeader String token){
        Map<String,Object> result=new HashMap<>();
        //String sessionid=session.getId();
        if(  this.redisTemplate.hasKey("cart_"+token)){
            Map<Object, Object> cart=this.redisTemplate.opsForHash().entries("cart_"+token);
            log.info("sessionid为"+token+",的购物车为:"+ cart );
            result.put("code",   1   );
            result.put("data",cart.values());   //keys中存的是 删除的商品编号
        }else{
            result.put("code",0);
        }
        return result;
    }

    @RequestMapping("addCart")//添加购物车
    public Map<String,Object> addCart(@RequestParam Integer fid,
                                      @RequestParam Integer num,
                                      @RequestHeader    String token){
        Map<String,Object> result=new HashMap<>();
        //String sessionid=session.getId();
        //log.info(sessionid);
        //TODO:到nacos中查找res-food服务中的 findbyid,得到菜品对象  (技术:服务发现)
        //方案一:利用url地址直接访问
        //String url="http://localhost:9001/resfood/findById/"+fid;
        //目标:发一个http请求url如上
        //socket->  urlconnection -> httpclient(针对http请求) ->resttemplate
        Resfood rf=null;
        //String url="http://localhost:9001/resfood/findById/"+fid;
        //方案二:利用服务名，通过服务发现功能查找url
//        String url="http://res-food/resfood/findById/"+fid;
//        Map<String,Object> resultMap=this.restTemplate.getForObject(url,Map.class);
        //方案三:利用openfeign来发出请求
        Map<String,Object> resultMap=this.resfoodApi.findById(fid);
        //TODO:   开启feign服务 指定feign的对象方法  注入resfoodApi 调用对应方法 （确定负载均衡 负载均衡冲突错误）发送请求
        //log.info("fault");
        if ("1".equalsIgnoreCase(resultMap.get("code").toString())){
            Map m= (Map) resultMap.get("obj");
            //如何将一个Map转为 一个Resfood对象  ->反射
            ObjectMapper mapper=new ObjectMapper();
            rf=mapper.convertValue(m,Resfood.class);
        }else {
            result.put("code",0);
            result.put("msg","查无此商品:"+fid);
            return result;
        }
        CartItem ci = (CartItem) this.redisTemplate.opsForHash().get("cart_"+token,fid+"");
        if (ci==null){
            ci=new CartItem();
            ci.setFood(rf);
            ci.setNum(num);
        }else {
            int newNum=ci.getNum()+num;
            ci.setNum(newNum);
        }
        if (ci.getNum()<=0){
            this.redisTemplate.opsForHash().delete(  "cart_"+token,  fid+"");
        }else {
            ci.getSmallCount();
            this.redisTemplate.opsForHash().put(   "cart_"+token, fid+"",  ci);
        }

//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        result.put("code",1);
        Map m=redisTemplate.opsForHash().entries("cart_"+token);
        result.put("data", m.values() );
        return result;
    }
}
