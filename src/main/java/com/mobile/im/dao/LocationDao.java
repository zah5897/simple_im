package com.mobile.im.dao;

import com.mobile.im.entity.Location;
import com.mobile.im.entity.Msg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by zah on 2018/6/22.
 */
@Repository
public class LocationDao extends BaseDao<Location> {

    public int getLocationCount(Location location) {
        return jdbcTemplate.queryForObject("select count(*) from t_location  where username=?", new Object[]{location.getUsername()}, Integer.class);
    }

    public int updateLocation(Location location) {
        String sql = "update t_location set lat=?,lng=?,addr=?,extMsg=?,update_time=? where username=?";
        return jdbcTemplate.update(sql, new Object[]{location.getLat(), location.getLng(), location.getAddr(), location.getExtMsg(), location.getUpdate_time(), location.getUsername()});
    }

    public List<Location> loadPublish(String username, int last_id, int pageSize) {
        String sql = "select *from t_location where username<> ? and id <?   order by id desc limit ?";
        return jdbcTemplate.query(sql, new Object[]{username, last_id, pageSize}, new BeanPropertyRowMapper<Location>(Location.class));

    }
}
