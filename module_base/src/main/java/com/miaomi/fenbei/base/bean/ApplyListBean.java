package com.miaomi.fenbei.base.bean;

public class ApplyListBean {



    /**
     * user_id : 10006
     * nickname : 手机用户7788
     * earning_total : 2027181
     * create_time : 2019-01-23
     */

    private int user_id;
    private String nickname;
    private int earning_total;
    private String create_time;
    private String face;
    private int age;
    private int gender;
    private int type;
    private CharmLevelBean charm_level;
    private WealthLevelBean wealth_level;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getEarning_total() {
        return earning_total;
    }

    public void setEarning_total(int earning_total) {
        this.earning_total = earning_total;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }
}
