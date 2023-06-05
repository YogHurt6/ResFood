package com.yc.api;

import com.yc.api.config.FeignLogConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value="res-food",path="resfood",configuration= FeignLogConfig.class)
public interface ResfoodApi {
    //openFeign支持springmvc的注解方式
    @RequestMapping("findByPage")
    public Map<String, Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize, @RequestParam String sortby, @RequestParam String sort);

    //参数很多必须用requestparam注解标注
    @RequestMapping("findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid);

    @RequestMapping("findAll")
    public Map<String, Object> findAll();

    //public xxxx impliment ResfoodApi{
    //  private Logger=new getLogger();
    //将以上接口 通过openfeign的动态代理生成实现类
    //public Map<String, Object> findById(@PathVariable Integer fid)
    //{  String url="http://"+value+path+RequestMapping();
    // restTemplate.getForObject(url,Map);}
    // }
}
