package com.pg.im.repository;

import com.pg.im.comm.OnlineStatus;
import com.pg.im.entity.UploadFile;
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
public interface UserRepository extends JpaRepository<User, Integer> {


    @Query(value="select count(*) from user where user_id=?1", nativeQuery = true)
    int getCountByUserId(String userId);

    @Query(value="select count(*) from user where user_id=?1 and password=?2", nativeQuery = true)
    int getCountByUserIdAndPassword(String userId,String password);

}
