package com.mobile.im.start.helper;

import com.mobile.im.service.UserService;
import com.mobile.im.util.SpringContextUtil;

/**
 * Created by zah on 2018/6/28.
 */
public class LocalServiceHelper {
    public static int imLogin(String userId, String token) {
        int r = ((UserService) SpringContextUtil.getBean("userService")).login(userId, token);
        return r;
    }
}
