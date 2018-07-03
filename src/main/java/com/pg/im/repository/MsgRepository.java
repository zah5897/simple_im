package com.pg.im.repository;

import com.pg.im.comm.ChatType;
import com.pg.im.comm.OnlineStatus;
import com.pg.im.entity.Msg;
import com.pg.im.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by zah on 2018/6/22.
 */
public interface MsgRepository extends JpaRepository<Msg, Integer> {

    @Query(value="select count(*) from histoy_msgs where finger_print=?1", nativeQuery = true)
    int historyMsgExist(String fingerPrint);


    @Query(value="select m.* from histoy_msgs m where (m._from=?1 and m.user_id=?2) or (m._from=?2 and m.user_id=?1) and m.id<?3 order by m.id desc limit ?4", nativeQuery = true)
    List<Msg> listMsg(String target,String userId,long last_id,int limit);


    @Query(value="select m.* from histoy_msgs m where  m.user_id=?1 and m.is_offline=1  order by m.id  limit ?2", nativeQuery = true)
    List<Msg> listOfflineMsg(String userId,int limit);


    @Query(value="select count(*) from histoy_msgs m where  m.user_id=?1 and  m.is_offline=1", nativeQuery = true)
    int getOfflineMsgCount(String userId);


    @Modifying
    @Transactional
    @Query("update Msg m set m.isOffline=?2 where m.userId=?1 and m.id=?3")
    int markOfflineToNormal(String userId,int isOffline, long id);
}
