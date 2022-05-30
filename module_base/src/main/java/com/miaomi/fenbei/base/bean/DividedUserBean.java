package com.miaomi.fenbei.base.bean;

import java.util.List;

public class DividedUserBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * face : http://im-file-oss.miaorubao.com/im/img/fenbei_default_face.png
         * nickname : 萌新用户1344
         * gender : 1
         * user_id : 100253
         * age : 18
         * divided : 60
         * charm_level : {"icon":"http://im-file-oss.94miao.com/im/img/avatar/1554890184_697.png","grade":1}
         * wealth_level : {"icon":"http://im-file-oss.94miao.com/im/img/avatar/1554890227_35.png","grade":1}
         */

        private String face;
        private String nickname;
        private int gender;
        private int user_id;
        private int age;
        private int divided;
        private CharmLevelBean charm_level;
        private WealthLevelBean wealth_level;
        private boolean isChecked;
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

        public int getDivided() {
            return divided;
        }

        public void setDivided(int divided) {
            this.divided = divided;
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
        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
        public static class CharmLevelBean {
            /**
             * icon : http://im-file-oss.94miao.com/im/img/avatar/1554890184_697.png
             * grade : 1
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
             * icon : http://im-file-oss.94miao.com/im/img/avatar/1554890227_35.png
             * grade : 1
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
    }
}
