package com.yc.resfood.configs;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//springboot的统一异常处理类，处理的是Controller中的异常
//此类实际上为Controller
@ControllerAdvice//controller控制器ioc Advice:aop中的增强
@Order(-100000)
public class CustomerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String,Object> handleRuntimeException(RuntimeException e){
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","RuntimeException occured");
        return map;
    }
}
