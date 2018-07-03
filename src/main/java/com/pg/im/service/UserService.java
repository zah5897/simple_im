package com.pg.im.service;

import com.pg.im.comm.OnlineStatus;
import com.pg.im.entity.User;
import com.pg.im.exception.ERROR;
import com.pg.im.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zah on 2018/6/22.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isExitUser(String userId) {
        int count = userRepository.getCountByUserId(userId);
        return count > 0;
    }

    private boolean checkPassword(String userId, String password) {
        int count = userRepository.getCountByUserIdAndPassword(userId, password);
        return count > 0;
    }

    public int login(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setCreate_time(new Date());
        return login(user);
    }

    /**
     * 登录&注册
     *
     * @param user
     * @return
     */
    public int login(User user) {
        if (isExitUser(user.getUserId())) {
            if (checkPassword(user.getUserId(), user.getPassword())) {
                return 0;
            } else {
                return ERROR.ERR_PASSWORD.getValue();
            }
        } else {
            userRepository.save(user);
            return 0;
        }
    }

}
