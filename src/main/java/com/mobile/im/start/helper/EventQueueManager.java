package com.mobile.im.start.helper;

import com.mobile.im.enums.OnlineStatus;
import com.mobile.im.service.MsgService;
import com.mobile.im.service.RedisService;
import com.mobile.im.start.event.MsgEvent;
import com.mobile.im.start.event.UserEvent;
import com.mobile.im.util.SpringContextUtil;

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
        redisService.saveUserLineStatus(event.getUserId(),event.getType()==0? OnlineStatus.ONLINE:OnlineStatus.OFFLINE);
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