package com.pg.im.start.helper;

import com.pg.im.comm.OnlineStatus;
import com.pg.im.entity.Msg;
import com.pg.im.service.MsgService;
import com.pg.im.service.RedisService;
import com.pg.im.start.event.BaseEvent;
import com.pg.im.start.event.MsgEvent;
import com.pg.im.start.event.UserEvent;
import com.pg.im.start.task.OfflineMsgPushTask;
import com.pg.im.util.SpringContextUtil;

import java.lang.ref.SoftReference;
import java.util.concurrent.*;

/**
 * Created by zah on 2018/6/25.
 */
public class EventQueueManager {
    private static EventQueueManager eventQueueManager = new EventQueueManager();
    private BlockingQueue<UserEvent> userEventQueue;
    private BlockingQueue<MsgEvent> msgEventQueue;

    private EventQueueManager() {
        userEventQueue = new LinkedBlockingQueue();
        msgEventQueue = new LinkedBlockingQueue();
        start();
    }

    public static EventQueueManager get() {
        return eventQueueManager;
    }

    public boolean putUserEvent(UserEvent event) {
        boolean offer= userEventQueue.offer(event);
        return  offer;
    }
    public boolean putMsgEvent(MsgEvent event) {
        boolean offer= msgEventQueue.offer(event);
        return  offer;
    }
    private void handleMsgEvent(MsgEvent event){
        ((MsgService)SpringContextUtil.getBean("msgService")).save(event);
    }
    private void handleUserEvent(UserEvent event){
        RedisService redisService=SpringContextUtil.getBean("redisService");
        redisService.saveUserLineStatus(event.getUserId(),event.getType()==0?OnlineStatus.ONLINE:OnlineStatus.OFFLINE);
    }

    private void start(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        handleUserEvent(userEventQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        handleMsgEvent(msgEventQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }



}