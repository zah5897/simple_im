package com.mobile.im.service;

import com.mobile.im.dao.MsgDao;
import com.mobile.im.entity.Location;
import com.mobile.im.entity.Msg;
import com.mobile.im.start.event.MsgEvent;
import com.mobile.im.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mobile.im.enums.MsgType.CMD;

/**
 * Created by zah on 2018/6/22.
 */
@SuppressWarnings("ALL")
@Service
public class MsgService {

    @Autowired
    private MsgDao msgDao;
    @Autowired
    private LocationService locationService;

    private boolean existMsg(String fingerPrint) {
        int count = msgDao.historyMsgExist(fingerPrint);
        return count > 0;
    }

    public void handleMsg(MsgEvent msgEvent) {
        if (msgEvent.getTypeu() == -1) {
            return;
        } else if (msgEvent.getTypeu() == CMD.ordinal()) {
            return;
        }
        if (existMsg(msgEvent.getFingerPrint())) { //排重
            return;
        }
        save(msgEvent);
    }

    public void save(MsgEvent msgEvent) {
        Msg msg = Msg.transToMsg(msgEvent);
        msgDao.insertObj(msg);
    }

    public List<Msg> getHistoryMsg(String target, String username, String fingerPrint, int limit) {
        long id = msgDao.getMsgIdByFingerprint(fingerPrint);
        List<Msg> msgs = msgDao.listMsg(target, username, id, limit);
        return msgs;
    }

    public Object getOfflineMsg(String username, String fingerPrint, int limit) {
        long id;
        if (TextUtils.isEmpty(fingerPrint)) {
            id = Long.MAX_VALUE;
        } else {
            id = msgDao.getMsgIdByFingerprint(fingerPrint);
        }

        List<Msg> msgs = msgDao.listOfflineMsg(username, id, limit);
        for (Msg msg : msgs) {
            msgDao.updateOffState(msg.getId(), 0);//设置为已读
        }
        return msgs;
    }

    public Object getOfflineMsgCount(String userId) {
        return msgDao.getOfflineMsgCount(userId);
    }


    public void markOfflineToNormal(String userId, String[] ids) {
        for (String id : ids) {
            try {
                msgDao.markOfflineToNormal(userId, Long.parseLong(id));
            } catch (NumberFormatException e) {
                //
            }
        }
    }
}
