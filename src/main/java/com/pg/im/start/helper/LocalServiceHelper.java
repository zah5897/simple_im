package com.pg.im.start.helper;

import com.pg.im.service.UserService;
import com.pg.im.util.SpringContextUtil;

/**
 * Created by zah on 2018/6/28.
 */
public class LocalServiceHelper {
    public static int login(String userId, String token, String s) {
       return  ((UserService) SpringContextUtil.getBean("userService")).login(userId,token);
    }
}
