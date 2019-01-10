package com.mobile.im.start.event;

import java.util.Date;

/**
 * Created by zah on 2018/6/25.
 */
public class MsgEvent extends BaseEvent {
    private int typeu;
    private String fingerPrint;
    private  String dataContent;
    private String from_user_id;
    private boolean isOffline=false;

    private long receiveTime;
    public MsgEvent(String userId) {
        super(userId);
    }

    public int getTypeu() {
        return typeu;
    }

    public void setTypeu(int typeu) {
        this.typeu = typeu;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }
}
