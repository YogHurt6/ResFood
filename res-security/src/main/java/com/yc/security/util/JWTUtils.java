package com.yc.security.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
//    public static final int EXPIRED_TOKEN = 409;
//    public static final int NO_TOKEN = 410;
//    public static final int INVALIDATE_TOKEN = 411;
//
//
//    public static long time = 1000 * 500;     //*60*24 //token 有效期为一天
//    private static String signature = "admin";
//
    private final static String SING="YC";

    public static String creatToken(Map<String,String> payload,int expireTime){
        JWTCreator.Builder builder= JWT.create();
        Calendar instance=Calendar.getInstance();//获取日历对象
        if(expireTime <=0) {
            instance.add(Calendar.SECOND,3600);//默认一小时
        } else {
            instance.add(Calendar.SECOND,expireTime);
        }
        //为了方便只放入了一种类型
        payload.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
    }

//    public static String createJwToken(Map<String, Object> claims, long expireTime) {
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        JwtBuilder builder = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(nowMillis + expireTime))
//                .signWith(SignatureAlgorithm.HS256, signature);
//
//        return builder.compact();
//    }

    public static Map<String, Object> getTokenInfo(String token){
        if( token==null||"".equals(token)){
            return null;
        }
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        Map<String, Claim> claims = verify.getClaims();
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expired= dateTime.format(verify.getExpiresAt());
        Map<String,Object> m=new HashMap<>();
        claims.forEach((k,v)-> m.put(k,v.asString()));
        m.put("exp",expired);
        return m;

    }
}
