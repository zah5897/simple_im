package com.pg.im.start.event;


import io.netty.channel.Channel;

/**
 * Created by zah on 2018/6/25.
 */
public class BaseEvent {

    public BaseEvent(String userId) {
        this.userId = userId;
    }
    private String userId;

    private String remoteAddress;
    private Channel session;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Channel getSession() {
        return session;
    }

    public void setSession(Channel session) {
        this.session = session;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
