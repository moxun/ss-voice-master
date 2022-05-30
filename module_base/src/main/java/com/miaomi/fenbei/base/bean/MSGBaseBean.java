package com.miaomi.fenbei.base.bean;


public class MSGBaseBean {
    private MsgType opt;
    private MsgType old_opt;
    private String chatId;

    public MsgType getOld_opt() {
        return old_opt;
    }

    public void setOld_opt(MsgType old_opt) {
        this.old_opt = old_opt;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public MsgType getOpt() {
        return opt;
    }

    public void setOpt(MsgType opt) {
        this.opt = opt;
    }

}
