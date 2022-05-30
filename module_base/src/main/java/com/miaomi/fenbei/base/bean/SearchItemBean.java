package com.miaomi.fenbei.base.bean;

public class SearchItemBean {



    private int user_id;
    private String name;
    private String icon;
    private int good_number;
    private int age;
    private int gender;
    private int id;
    private int locked;
    private int status;
    private LevelBean charm_level;
    private LevelBean wealth_level;
    private long create_time;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
