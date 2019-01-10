package com.mobile.im.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by zah on 2018/6/22.
 */
public enum ChatType {
    Chat, GroupChat, ChatRoom;

    @JsonValue
    public int getValue() {
        return this.ordinal();
    }
}
