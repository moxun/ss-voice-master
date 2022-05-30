package com.miaomi.fenbei.base.bean;

public class AppearBean {
    private boolean show_toast;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShow_toast() {
        return show_toast;
    }

    public void setShow_toast(boolean show_toast) {
        this.show_toast = show_toast;
    }
}
