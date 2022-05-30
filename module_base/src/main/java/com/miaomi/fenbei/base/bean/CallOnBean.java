package com.miaomi.fenbei.base.bean;

public class CallOnBean {



    private int user_id;
    private int create_time;
    private String face;
    private String nickname;
    private int age;
    private int gender;
    private int good_number;
    private LevelBean charm_level;
    private LevelBean wealth_level;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public LevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(LevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public LevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(LevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

}
