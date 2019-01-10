package com.mobile.im.service;

import com.mobile.im.dao.UserDao;
import com.mobile.im.entity.User;
import com.mobile.im.exception.ERROR;
import com.mobile.im.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by zah on 2018/6/22.
 */
@SuppressWarnings("ALL")
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean isExitUser(String username) {
        int count = userDao.getCountByUserName(username);
        return count > 0;
    }

    private boolean checkPassword(String username, String password) {
        int count = userDao.getLoginCount(username, password);
        return count > 0;
    }

    public int regist(String username, String password) {
        if (isExitUser(username)) {
            return ERROR.ERR_USER_EXIST.ordinal();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreate_time(new Date());
        int r = userDao.insertObj(user);
        return ERROR.ERR_NO_ERR.ordinal();
    }

    public int login(String username, String password) {
        String md5 = "";
        try {
            md5 = MD5Util.getMd5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ERROR.ERR_PASSWORD.getValue();
        }
        if (isExitUser(username)) {
            if (checkPassword(username, md5)) {
                return 0;
            } else {
                return ERROR.ERR_PASSWORD.getValue();
            }
        } else {
            return ERROR.ERR_USER_NOT_EXIST.ordinal();
        }
    }
}
