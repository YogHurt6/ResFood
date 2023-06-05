package com.yc.resfood.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.resfood.biz.ResfoodBiz;
import com.yc.web.model.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("resfood")  //http://localhost:port/resfood
@Slf4j
public class ResfoodController {
    @Autowired
    private ResfoodBiz resfoodBiz;

    @RequestMapping("findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //System.out.println(token);
        Map<String, Object> map = new HashMap<>();
        Resfood rf = null;
        try {
            rf = this.resfoodBiz.findById(fid);
        } catch (Exception ex) {
            map.put("code", 0);
            map.put("msg", ex.getCause());
            return map;
        }
        map.put("code", 1);
        map.put("obj", rf);
        return map;
    }
//    @RequestMapping("findById/{fid}")
//    public Map<String, Object> findById(@PathVariable Integer fid) {
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        Map<String, Object> map = new HashMap<>();
//        Resfood rf = null;
//        try {
//            rf = this.resfoodBiz.findById(fid);
//        } catch (Exception ex) {
//            map.put("code", 0);
//            map.put("msg", ex.getCause());
//            return map;
//        }
//        map.put("code", 1);
//        map.put("obj", rf);
//        return map;
//    }

//    private Map<String, Object> exceptionFallback(Throwable throwable){
//        throwable.printStackTrace();
//        Map<String, Object> map = new HashMap<>();
//        map.put("code",0);
//        map.put("msg","resource is under exception");
//        return map;
//    }

    public Set<Thread> set=new HashSet<>();
    @SentinelResource(value = "findAll",fallback = "exceptionFallback")
    @RequestMapping("findAll")
    public Map<String, Object> findAll() {
//        int flag=1;
//        if (flag==1){
//            throw new RuntimeException("业务异常: 查找所有出异常了");
//        }
        Thread thread=Thread.currentThread();//获取当前的tomcat处理这个请求的线程
        set.add(thread);
        log.info("线程数为:"+set.size()+"，当前线程编号为"+thread.getId());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        List<Resfood> list = null;
        try {
            list = this.resfoodBiz.findAll();
        } catch (Exception ex) {
            map.put("code", 0);
            map.put("msg", ex.getCause());
            return map;
        }
        map.put("code", 1);
        map.put("obj", list);
        return map;
    }

//    public Map<String,Object> handBlock(@RequestParam int pageno,
//                                        @RequestParam int pagesize,
//                                        @RequestParam String sortby,
//                                        @RequestParam String sort ,
//                                        BlockException exception){
//        exception.printStackTrace();
//        Map<String,Object> map=new HashMap<>();
//        map.put("code",1);
//        map.put("msg","资源:"+exception.getRuleLimitApp()+":被限流，规则为:"+exception.getRule().toString());
//        return map;//sentinel:200
//    }

    @RequestMapping("findByPage")
//    @SentinelResource(value = "hotkey-page",blockHandler = "handBlock")//流控资源名  热点资源
    public Map<String, Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize, @RequestParam String sortby, @RequestParam String sort   ) {
        Map<String, Object> map = new HashMap<>();
        Page<Resfood> page = null;
        try {
            page = this.resfoodBiz.findByPage(pageno, pagesize, sortby, sort);
        } catch (Exception ex) {
            map.put("code", 0);
            map.put("msg", ex.getCause());
            return map;
        }
        map.put("code", 1);
        PageBean pageBean=new PageBean();//mybatis的分页与当前项目分页的数据不符，当前项目使用pagebean封装
        pageBean.setTotal(page.getTotal());
        pageBean.setPageno(pageno);
        pageBean.setSort(sort);
        pageBean.setSortby(sortby);
        pageBean.setDataset(page.getRecords());
        // 其它的分页数据
        //计算总页数
        long  totalPages=page.getTotal()%pageBean.getPagesize()==0?page.getTotal()/pageBean.getPagesize():page.getTotal()/pageBean.getPagesize()+1;
        pageBean.setTotalpages(    (int)totalPages);
        //上一页页号的计算
        if( pageBean.getPageno()<=1){
            pageBean.setPre(     1  );
        }else{
            pageBean.setPre(    pageBean.getPageno()-1);
        }
        //下一页的页号
        if(   pageBean.getPageno()  == totalPages ){
            pageBean.setNext(   (int)totalPages );
        }else{
            pageBean.setNext(    pageBean.getPageno()+1);
        }

        map.put("data", pageBean);
        return map;
    }
}
