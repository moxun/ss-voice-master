package com.miaomi.fenbei.base.bean.event;

public class RefreshUnReadMsgBean {
    private String uid;

    public RefreshUnReadMsgBean(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
