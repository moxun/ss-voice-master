package com.miaomi.fenbei.base.bean;

public class FamilyRankBean {



    private String face;
    private String nickname;
    private String signature;
    private int gender;
    private int age;
    private int user_id;
    private String earning_total;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEarning_total() {
        return earning_total;
    }

    public void setEarning_total(String earning_total) {
        this.earning_total = earning_total;
    }
}
