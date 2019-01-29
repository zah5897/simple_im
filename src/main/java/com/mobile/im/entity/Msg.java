package com.mobile.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobile.im.annotation.Id;
import com.mobile.im.enums.ChatType;
import com.mobile.im.enums.MsgType;
import com.mobile.im.start.event.MsgEvent;

import java.util.Date;

import static com.mobile.im.enums.MsgType.values;

/**
 * Created by zah on 2018/6/22.
 */
public class Msg {
    @Id
    private long id;
    private String _from;
    private String username;
    private long receiveTime;
    private ChatType chatType = ChatType.Chat;
    private MsgType msgType = MsgType.TXT;

    private int offState = 0;  //1 为离线消息

    private String content;

    private String fingerPrint;


    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getOffState() {
        return offState;
    }

    public void setOffState(int offState) {
        this.offState = offState;
    }


    public static Msg transToMsg(MsgEvent event){
        Msg msg = new Msg();
        msg.set_from(event.getFrom_user_id());
        msg.setUsername(event.getUserId());
        msg.setChatType(ChatType.Chat);
        msg.setFingerPrint(event.getFingerPrint());
        msg.setReceiveTime(event.getReceiveTime());
        msg.setContent(event.getDataContent());
        msg.setOffState(event.isOffline() ? 1 : 0);
        int type=event.getTypeu();
        msg.setMsgType(values()[type]); //默认-1
        return msg;
    }
}
