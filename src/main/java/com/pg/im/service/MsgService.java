package com.pg.im.service;

import com.pg.im.comm.ChatType;
import com.pg.im.comm.MsgType;
import com.pg.im.entity.Msg;
import com.pg.im.repository.MsgRepository;
import com.pg.im.start.event.MsgEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by zah on 2018/6/22.
 */
@Service
public class MsgService {

    @Autowired
    private MsgRepository msgRepository;


    private boolean existMsg(String fingerPrint) {
        int count=msgRepository.historyMsgExist(fingerPrint);
        return  count> 0;
    }

    public void save(MsgEvent msgEvent) {
        if (existMsg(msgEvent.getFingerPrint())) { //排重
            return;
        }
        Msg msg = new Msg();
        msg.set_from(msgEvent.getFrom_user_id());
        msg.setUserId(msgEvent.getUserId());
        if (msgEvent.getTypeu() < 100) {
            msg.setChatType(ChatType.CHAT_USER);
        }
        msg.setFingerPrint(msgEvent.getFingerPrint());
        msg.setSendTime(msgEvent.getSendTime());
        msg.setDataContent(msgEvent.getDataContent());
        msg.setOffline(msgEvent.isOffline());
        msg.setMsgType(MsgType.values()[msgEvent.getTypeu()+1]); //默认-1
        msgRepository.save(msg);
    }

    public List<Msg> getHistoryMsg(String target, String userId, long last_id, int limit) {
        List<Msg> msgs= msgRepository.listMsg(target,userId,last_id,limit);
        Collections.reverse(msgs);
        return msgs;
    }

    public Object getOfflineMsg(String userId, int limit) {
        List<Msg> msgs= msgRepository.listOfflineMsg(userId,limit);
        return msgs;
    }
    public Object getOfflineMsgCount(String userId) {
        return  msgRepository.getOfflineMsgCount(userId);
    }


    public void markOfflineToNormal(String userId,String[] ids){
         for(String id:ids){
             try {
                 msgRepository.markOfflineToNormal(userId,0,Long.parseLong(id));
             }catch (NumberFormatException e){
                 //
             }
         }
    }
}
