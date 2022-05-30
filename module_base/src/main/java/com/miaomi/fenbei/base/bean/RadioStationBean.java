package com.miaomi.fenbei.base.bean;

import java.io.Serializable;

public class RadioStationBean implements Serializable {


    /**
     * id : 1
     * time_period_start : 00:00
     * radio_name : 电台名称
     * today_topic : 今日话题
     * introduction : 电台简介
     * icon :
     * room_id : 123467
     * status : 0
     * time_period_end : 02:00
     * user_id : 100251
     * push_date : 2021-06-06
     * create_time : 1622718117
     * update_time : 1622718117
     * nickname : 测试
     * face : http://im-file-oss.miaorubao.com/im_test/img/avatar/1002511618841240058.jpg
     * fans : 2
     */

    private int id;
    private String time_period_start;
    private String radio_name;
    private String today_topic;
    private String introduction;
    private String icon;
    private int room_id;
    private int status;
    private String time_period_end;
    private int user_id;
    private String push_date;
    private int create_time;
    private int update_time;
    private String nickname;
    private String face;
    private int fans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_period_start() {
        return time_period_start;
    }

    public void setTime_period_start(String time_period_start) {
        this.time_period_start = time_period_start;
    }

    public String getRadio_name() {
        return radio_name;
    }

    public void setRadio_name(String radio_name) {
        this.radio_name = radio_name;
    }

    public String getToday_topic() {
        return today_topic;
    }

    public void setToday_topic(String today_topic) {
        this.today_topic = today_topic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime_period_end() {
        return time_period_end;
    }

    public void setTime_period_end(String time_period_end) {
        this.time_period_end = time_period_end;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPush_date() {
        return push_date;
    }

    public void setPush_date(String push_date) {
        this.push_date = push_date;
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

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }
}
