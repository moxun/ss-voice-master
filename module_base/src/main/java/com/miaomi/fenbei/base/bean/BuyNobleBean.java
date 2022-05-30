package com.miaomi.fenbei.base.bean;

public class BuyNobleBean {



    private int user_id;
    private int rank;
    private int create_time;
    private int update_time;
    private long expired_at;
    private String svg;
    private String msg = "";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public long getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(long expired_at) {
        this.expired_at = expired_at;
    }
}
