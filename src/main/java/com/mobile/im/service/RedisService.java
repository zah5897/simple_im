package com.mobile.im.service;

import com.mobile.im.enums.OnlineStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zah on 2018/6/26.
 */
@Service
public class RedisService {
    @Autowired

    public static final String USER_NET_STATUS = "user_net_status";


    public void saveUserLineStatus(String username, OnlineStatus onlineStatus) {
        try {
//            redisTemplate.opsForHash().put(USER_NET_STATUS, username, onlineStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OnlineStatus getUserLineStatus(String username) {
        try {
            Object obj = null;
//            Object obj = redisTemplate.opsForHash().get(USER_NET_STATUS, username);
            if (obj == null) {
                return OnlineStatus.OFFLINE;
            }
            return (OnlineStatus) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return OnlineStatus.OFFLINE;
        }
    }

}
