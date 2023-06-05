package com.yc.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.security.dao.ResuserDao;
import com.yc.security.util.JWTUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ResuserController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResuserDao resuserDao;

    private int expireTime = 3600;

    @GetMapping("/checkLogin.action")
    public Map checkLogin(@RequestHeader String token) throws ServletException, IOException {
        log.info("待检测的token为:"+ token );
        Map map = new HashMap();
        if( "".equals(token)){
            map.put("code",0);
            return map;
        }
        boolean flag=this.redisTemplate.hasKey(token);
        if(  flag) {
            map.put("code", 1);
            String t= (String) this.redisTemplate.opsForHash().get(token,"token");
            Map<String,Object> info=JWTUtils.getTokenInfo(   t   );
            map.put(   "data",info);
        }else{
            map.put("code",0);
        }
        return map;
    }

    @PostMapping("/logout")
    public Map logout(@RequestHeader String token) throws ServletException, IOException {
        this.redisTemplate.delete(token);
        Map map = new HashMap();
        map.put("code", 1);
        return map;
    }

    @PostMapping("/resuser.action")
    public Map login(Resuser user, HttpServletRequest request) throws ServletException, IOException {
        Map map = new HashMap();
        //用户输入的验证码
        String valcode = request.getParameter("valcode");
        //取出标准验证码
        HttpSession session = request.getSession();
        String code = session.getAttribute("code").toString();
        //验证码判断
        if (!code.equals(valcode)) {
            map.put("code", 0);
            map.put("msg", "验证码错误");
            return map;
        }
        QueryWrapper qw = new QueryWrapper();
        qw.eq("username", user.getUsername());
        qw.eq("pwd", DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
        Resuser resuser = resuserDao.selectOne(qw);
        if (resuser != null) {
            map.put("code", 1);
            Map m = new HashMap();
            m.put("username", user.getUsername());
            m.put("userid", user.getUserid());
            String token = JWTUtils.creatToken(m, expireTime);

            Map obj = new HashMap<String, Object>();
            obj.put("token", token);
            obj.put("username", user.getUsername());

            map.put("data", obj);

            redisTemplate.opsForHash().put(token, token, "");
            redisTemplate.expire(token, expireTime, TimeUnit.SECONDS);
            return map;
        }
        map.put("code", 0);
        map.put("msg", "查无此用户名和密码");
        return map;
    }
}
