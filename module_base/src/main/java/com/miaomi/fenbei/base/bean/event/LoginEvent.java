package com.miaomi.fenbei.base.bean.event;

public class LoginEvent {
    private boolean loginStatus;

    public LoginEvent(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }
}
