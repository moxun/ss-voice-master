package com.miaomi.fenbei.base.bean.event;

public class NetWorkBean {
    boolean isConnect;

    public NetWorkBean(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
