package com.mobile.im.start.event;

public class SysEvent {
    private int typeu;
    private String fingerPrint;
    private String dataContent;
    private String from_user_id;
    private long receiveTime;


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


    public static enum SysType {
        PUBLISH_LOCATION(1000);
        private int value;

        private SysType(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
