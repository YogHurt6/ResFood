package com.yc.resfood.controller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("service")
@RefreshScope//实时刷新
public class ServiceController {

    @Value("${res.pattern.dateFormate}")
    private String dateFormate;

    @RequestMapping("nowTime")
    public Map<String, Object> nowTime() {
        Map<String,Object> map=new HashMap<>();
        Date date=new Date();
        DateFormat df=new SimpleDateFormat(dateFormate);

        map.put("code",1);
        map.put("obj",df.format(date));
        return map;
    }
}
