package com.miaomi.fenbei.base.bean;


public class FriendsBean  {
    private String face;
    private int gender;
    private String icon;
    private String nickname;
    private String name;
    private String signature;
    private int state;
    private String user_id;
    private String room_id;
    private int age;
    private String tag;
    private boolean isTop;//是否在房间中 不需要被转化成拼音的

    private CharmLevelBean charm_level;
    private WealthLevelBean wealth_level;
    public CharmLevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(CharmLevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public WealthLevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(WealthLevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

    public static class CharmLevelBean {


        private String icon;
        private int grade;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }

    public static class WealthLevelBean {


        private String icon;
        private int grade;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }

    public boolean isTop() {
        return isTop;
    }

    public FriendsBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FriendsBean setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
