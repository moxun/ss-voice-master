package com.miaomi.fenbei.base.bean;

public class RoomGiftHistoryBean {


    private int num;
    private String send_face;
    private String get_face;
    private String send_nickname;
    private String get_nickname;
    private String gift_icon;
    private long time;
    private int d_fan_type;

    public int getD_fan_type() {
        return d_fan_type;
    }

    public void setD_fan_type(int d_fan_type) {
        this.d_fan_type = d_fan_type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSend_face() {
        return send_face;
    }

    public void setSend_face(String send_face) {
        this.send_face = send_face;
    }

    public String getGet_face() {
        return get_face;
    }

    public void setGet_face(String get_face) {
        this.get_face = get_face;
    }

    public String getSend_nickname() {
        return send_nickname;
    }

    public void setSend_nickname(String send_nickname) {
        this.send_nickname = send_nickname;
    }

    public String getGet_nickname() {
        return get_nickname;
    }

    public void setGet_nickname(String get_nickname) {
        this.get_nickname = get_nickname;
    }

    public String getGift_icon() {
        return gift_icon;
    }

    public void setGift_icon(String gift_icon) {
        this.gift_icon = gift_icon;
    }
}
