package com.mobile.im.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MsgType {
    TXT, IMG, AUDIO;

    @JsonValue
    public int getValue() {
        return this.ordinal();
    }
}