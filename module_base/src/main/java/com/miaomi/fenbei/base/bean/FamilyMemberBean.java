package com.miaomi.fenbei.base.bean;


public class FamilyMemberBean {


    /**
     * face : http://im-file-oss.7guoyouxi.com/im_test/img/avatar/b554f5255859743d190378d096f5f360.jpg
     * nickname : 手机用户
     * gender : 2
     * user_id : 10030
     * age : 5
     * signature : 4566677）884566677）88
     * type : 2
     */

    private String face;
    private String nickname;
    private int gender;
    private int user_id;
    private int age;
    private String signature;
    private int type;
    private String room_id;
    private int good_number;
    private int good_number_state;
    private boolean isChecked;
    private String divided;
    public int getGood_number_state() {
        return good_number_state;
    }

    public void setGood_number_state(int good_number_state) {
        this.good_number_state = good_number_state;
    }

    private FriendsBean.CharmLevelBean charm_level;
    private FriendsBean.WealthLevelBean wealth_level;
    public FriendsBean.CharmLevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(FriendsBean.CharmLevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public FriendsBean.WealthLevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(FriendsBean.WealthLevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

    public static class CharmLevelBean {
        /**
         * icon : http://im-file-oss.7guoyouxi.com/im/img/avatar/1554097319_483.png
         * grade : 19
         */

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
        /**
         * icon : 公爵
         * grade : 17
         */

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

    public String getDivided() {
        return divided;
    }

    public void setDivided(String divided) {
        this.divided = divided;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
