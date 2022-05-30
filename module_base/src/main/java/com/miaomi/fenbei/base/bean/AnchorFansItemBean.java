package com.miaomi.fenbei.base.bean;

public class AnchorFansItemBean {

    private String user_id;
    private String sort;
    private int hot_value;
    private String nickname;
    private String face;
    private int day;
    private String start;
    private String end;
    private String fans_num;
    private int type;
    private String fans_end_time = "";

    public String getFans_end_time() {
        return fans_end_time;
    }

    public void setFans_end_time(String fans_end_time) {
        this.fans_end_time = fans_end_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getHot_value() {
        return hot_value;
    }

    public void setHot_value(int hot_value) {
        this.hot_value = hot_value;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFans_num() {
        return fans_num;
    }

    public void setFans_num(String fans_num) {
        this.fans_num = fans_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
