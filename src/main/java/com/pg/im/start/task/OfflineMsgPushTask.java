package com.pg.im.start.task;

import com.pg.im.service.MsgService;
import com.pg.im.util.SpringContextUtil;

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
