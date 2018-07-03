package com.pg.im.service;

import com.pg.im.comm.OnlineStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Created by zah on 2018/6/26.
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public static final  String USER_NET_STATUS="user_net_status";


    public void saveUserLineStatus(String username, OnlineStatus onlineStatus){
        redisTemplate.opsForHash().put(USER_NET_STATUS,username,onlineStatus);
    }


    public OnlineStatus getUserLineStatus(String username){
       Object obj= redisTemplate.opsForHash().get(USER_NET_STATUS,username);
       if(obj==null){
           return  OnlineStatus.OFFLINE;
       }
       return (OnlineStatus) obj;
    }

}
