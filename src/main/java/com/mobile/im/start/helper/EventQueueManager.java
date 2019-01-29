package com.mobile.im.start.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobile.im.entity.Location;
import com.mobile.im.enums.MsgType;
import com.mobile.im.enums.OnlineStatus;
import com.mobile.im.service.LocationService;
import com.mobile.im.service.MsgService;
import com.mobile.im.service.RedisService;
import com.mobile.im.start.event.MsgEvent;
import com.mobile.im.start.event.SysEvent;
import com.mobile.im.start.event.UserEvent;
import com.mobile.im.util.JSONUtil;
import com.mobile.im.util.SpringContextUtil;
import net.openmob.mobileimsdk.server.utils.LocalSendHelper;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by zah on 2018/6/25.
 */
public class EventQueueManager {
    private static EventQueueManager eventQueueManager = new EventQueueManager();
    private BlockingQueue<UserEvent> userEventQueue;
    private BlockingQueue<MsgEvent> msgEventQueue;
    private BlockingQueue<SysEvent> sysEventQueue;
    MsgService msgService;
    LocationService locationService;

    private EventQueueManager() {
        userEventQueue = new LinkedBlockingQueue();
        msgEventQueue = new LinkedBlockingQueue();
        sysEventQueue = new LinkedBlockingQueue();
        start();
    }

    public static EventQueueManager get() {
        return eventQueueManager;
    }


    public MsgService getMsgService() {
        if (msgService == null) {
            msgService = SpringContextUtil.getBean("msgService");
        }
        return msgService;
    }

    public LocationService getLocationService() {
        if (locationService == null) {
            locationService = SpringContextUtil.getBean("locationService");
        }
        return locationService;
    }

    public boolean putUserEvent(UserEvent event) {
        boolean offer = userEventQueue.offer(event);
        return offer;
    }

    public boolean putMsgEvent(MsgEvent event) {
        boolean offer = msgEventQueue.offer(event);
        return offer;
    }

    public boolean putSysEvent(SysEvent event) {
        boolean offer = sysEventQueue.offer(event);
        return offer;
    }

    private void handleMsgEvent(MsgEvent event) {
        getMsgService().handleMsg(event);
    }

    private void handleUserEvent(UserEvent event) {
        RedisService redisService = SpringContextUtil.getBean("redisService");
        redisService.saveUserLineStatus(event.getUserId(), event.getType() == 0 ? OnlineStatus.ONLINE : OnlineStatus.OFFLINE);
    }

    private void handleSysEvent(SysEvent event) {
        int typeu = event.getTypeu();
        if (typeu == SysEvent.SysType.PUBLISH_LOCATION.getValue()) {

            Location location = JSONUtil.jsonToObj(event.getDataContent(), new TypeReference<Location>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            location.setUsername(event.getFrom_user_id());
            location.setUpdate_time(new Date());
            getLocationService().save(location);

            if (location.getType() == 0) {
                //只是上传位置
            } else if (location.getType() == 1) { //发给某人
                Map<String, String> sendMsg = new HashMap<>();
                sendMsg.put("type", "0");
                sendMsg.put("lat", location.getLat());
                sendMsg.put("lng", location.getLng());
                sendMsg.put("from", event.getFrom_user_id());
                sendMsg.put("serverTime", String.valueOf(System.currentTimeMillis() / 1000));
                try {
                    LocalSendHelper.sendData(location.getTo(), JSONUtil.writeValueAsString(sendMsg), typeu, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void start() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handleUserEvent(userEventQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handleMsgEvent(msgEventQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handleSysEvent(sysEventQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


}