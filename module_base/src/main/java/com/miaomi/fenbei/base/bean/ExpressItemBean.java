package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ExpressItemBean {



    private int num;
    private int id;
    private String gift_name;
    private String send_face;
    private String get_face;
    private String send_nickname;
    private String get_nickname;
    private String gift_icon;
    private long time;
    private String note;
    private boolean heart_status;
    private int heart_num;
    private List<String> hearts;
    private int send_good_number;
    private int get_good_number;
    private String send_seat_frame;
    private String get_seat_frame;
    private int get_uid;
    private int send_uid;

    public int getGet_uid() {
        return get_uid;
    }

    public void setGet_uid(int get_uid) {
        this.get_uid = get_uid;
    }

    public int getSend_uid() {
        return send_uid;
    }

    public void setSend_uid(int send_uid) {
        this.send_uid = send_uid;
    }

    public int getSend_good_number() {
        return send_good_number;
    }

    public void setSend_good_number(int send_good_number) {
        this.send_good_number = send_good_number;
    }

    public int getGet_good_number() {
        return get_good_number;
    }

    public void setGet_good_number(int get_good_number) {
        this.get_good_number = get_good_number;
    }

    public String getSend_seat_frame() {
        return send_seat_frame;
    }

    public void setSend_seat_frame(String send_seat_frame) {
        this.send_seat_frame = send_seat_frame;
    }

    public String getGet_seat_frame() {
        return get_seat_frame;
    }

    public void setGet_seat_frame(String get_seat_frame) {
        this.get_seat_frame = get_seat_frame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isHeart_status() {
        return heart_status;
    }

    public void setHeart_status(boolean heart_status) {
        this.heart_status = heart_status;
    }

    public int getHeart_num() {
        return heart_num;
    }

    public void setHeart_num(int heart_num) {
        this.heart_num = heart_num;
    }

    public List<String> getHearts() {
        return hearts;
    }

    public void setHearts(List<String> hearts) {
        this.hearts = hearts;
    }
}
