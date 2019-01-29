package com.mobile.im.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MsgType {
    TXT, IMG, AUDIO,LOCAION,FILE,CMD;

    @JsonValue
    public int getValue() {
        return this.ordinal();
    }
}