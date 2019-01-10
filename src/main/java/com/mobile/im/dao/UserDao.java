package com.mobile.im.dao;


import com.mobile.im.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by zah on 2018/6/22.
 */
@Repository
public class UserDao extends BaseDao<User> {

    public int getCountByUserName(String username) {
        String sql = "select count(*) from t_user where username=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
    }

    public int getLoginCount(String username, String password) {
        String sql = "select count(*) from t_user where username=? and password=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);
    }
}
