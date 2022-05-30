package com.miaomi.fenbei.base.bean;

public class MakeFriendBean {


    private int user_id;
    private int gender;
    private String face;
    private String nickname;
    private int wealth_level;
    private String wealth_icon;
    private String content;
    private int room_id;
    private String medal;


    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public MakeFriendBean(MsgBean bean) {
        this.face = bean.getFromUserInfo().getFace();
        this.gender = bean.getFromUserInfo().getGender();
        this.nickname = bean.getFromUserInfo().getNickname();
        this.wealth_level = bean.getFromUserInfo().getWealth_level().getGrade();
        this.wealth_icon = bean.getFromUserInfo().getWealth_level().getIcon();
        this.content = bean.getContent();
        this.room_id = Integer.parseInt(bean.getRoomId());
        this.user_id = bean.getFromUserInfo().getUser_id();
        this.medal = bean.getFromUserInfo().getMedal();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(int wealth_level) {
        this.wealth_level = wealth_level;
    }

    public String getWealth_icon() {
        return wealth_icon;
    }

    public void setWealth_icon(String wealth_icon) {
        this.wealth_icon = wealth_icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
