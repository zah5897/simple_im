package com.mobile.im.start.event;

/**
 * Created by zah on 2018/6/25.
 */
public class UserEvent extends BaseEvent {
    private String token;
    private String extra;
    private int type; //-1 logout,0 login,
    public UserEvent(String userId, String extra,String remoteAddress,int type) {
        super(userId);
        this.extra=extra;
        this.type=type;
        setRemoteAddress(remoteAddress);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
