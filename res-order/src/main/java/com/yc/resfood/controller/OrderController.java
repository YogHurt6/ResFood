package com.yc.resfood.controller;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.resfood.biz.ResorderBiz;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RequestMapping("order")
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResorderBiz resorderBiz;

    @PostMapping("orderFood")  //    /order/orderFood
    public Map<String,Object> orderFood(Resorder resorder, @RequestHeader String token){
        Map<String,Object> map=new HashMap<>( );


        if(  !this.redisTemplate.hasKey("cart_"+token)|| this.redisTemplate.opsForHash().entries("cart_"+token).size()<=0){
            map.put("code",0);
            return map;
        }
        Map<String, CartItem> cart=this.redisTemplate.opsForHash().entries(  "cart_"+token );
        Collection<CartItem> cis= (Collection<CartItem>) cart.values();
        //TODO:调用业务层完成订单记录操作
        Resuser user=new Resuser();
        //将来这个userid也可以从登录信息中取出

        //Map<String,Object> info=JWTUtils.getTokenInfo(   token   );
        //int userid=  Integer.parseInt(info.get("userid").toString());

        //user.setUserid( userid );

        //处理下订时间
        // 创建LocalDateTime对象，表示当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 创建DateTimeFormatter对象，指定格式为"yyyy-MM-dd HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // 调用format方法，将LocalDateTime对象转换为字符串
        resorder.setOrdertime( formatter.format(now)  );
        LocalDateTime deliveryTime=now.plusHours(5);
        resorder.setDeliverytime(  formatter.format(deliveryTime)     );
        resorder.setStatus(0);

        resorderBiz.order(  resorder ,  new HashSet( cis ),  user  );
        double total=0;
        for( CartItem ci:cis){
            total+=ci.getSmallCount();
        }
        this.redisTemplate.delete(   "cart_"+token );
        map.put("code",1);
        return map;
    }
    //支付在这里调用银联的支付接口
    @GetMapping("payAction")
    public Map<String,Object> payAction(Integer integer)throws InterruptedException{
        //TODO: 测试慢请求
        if (integer==null){
            //Thread.sleep(1000);
            throw new RuntimeException("出异常了");
        }
        Map<String ,Object> map=new HashMap<>();
        //取出当前用户的订单金额，调用第三方接口，完成支付
        map.put("code",1);
        return map;
    }
}
