package com.mobile.im.dao;

import com.mobile.im.entity.Msg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by zah on 2018/6/22.
 */
@Repository
public class MsgDao extends BaseDao<Msg> {

    public int historyMsgExist(String fingerPrint) {
        return jdbcTemplate.queryForObject("select count(*) from t_msg where fingerprint=?", new Object[]{fingerPrint}, Integer.class);
    }

    public List<Msg> listMsg(String target, String username, long last_id, int limit) {
        String sql = "select m.* from t_msg m where (m._from=? and m.username=?) or (m._from=? and m.username=?) and m.id<? order by m.id  limit ?";
        return jdbcTemplate.query(sql, new Object[]{target, username, username, target, last_id, limit}, new BeanPropertyRowMapper<Msg>(Msg.class));
    }


    public List<Msg> listOfflineMsg(String username, long last_id, int limit) {
        String sql = "select m.* from t_msg m where  m.username=? and m.offState=?  and m.id<?  order by m.id   limit ?";
        return jdbcTemplate.query(sql, new Object[]{username, 1, last_id, limit}, new BeanPropertyRowMapper<Msg>(Msg.class));
    }

    public void updateOffState(long id, int state) {
        String sql = "update t_msg set offState=?  where id=?";
        jdbcTemplate.update(sql, new Object[]{state, id});
    }

    public int getOfflineMsgCount(String username) {
        return jdbcTemplate.queryForObject("select count(*) from t_msg m where  m.username=? and  m.offState=?", new Object[]{username, 1}, Integer.class);
    }

    public long getMsgIdByFingerprint( String fingerprint) {
        List<Long> ids = jdbcTemplate.queryForList("select id from t_msg   where  fingerprint=?", new Object[]{fingerprint}, Long.class);
        if (!ids.isEmpty()) {
            return ids.get(0);
        }
        return 0;
    }


    public int markOfflineToNormal(String username, long id) {
        return jdbcTemplate.update("update t_msg set offState=? where username=? and id=?", new Object[]{0, username, id});
    }
}
