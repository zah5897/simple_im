package com.mobile.im.start.task;

/**
 * Created by zah on 2018/6/25.
 */
public class OfflineMsgPushTask implements Runnable {
    private String username;
    public OfflineMsgPushTask(String username){
      this.username=username;
    }
    @Override
    public void run() {
    }
}
