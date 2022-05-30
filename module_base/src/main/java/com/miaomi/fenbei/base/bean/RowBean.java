package com.miaomi.fenbei.base.bean;

import java.util.List;

public class RowBean {


    private List<ZhuListBean> zhu_list;
    private List<CiListBean> ci_list;

    public List<ZhuListBean> getZhu_list() {
        return zhu_list;
    }

    public void setZhu_list(List<ZhuListBean> zhu_list) {
        this.zhu_list = zhu_list;
    }

    public List<CiListBean> getCi_list() {
        return ci_list;
    }

    public void setCi_list(List<CiListBean> ci_list) {
        this.ci_list = ci_list;
    }

    public static class ZhuListBean {
        /**
         * user_id : 100252
         * nickname : 萌新用户5501
         * face : http://im-file-oss.miaorubao.com/im/img/gift_icon/1611049395_57.png
         */

        private int user_id;
        private String nickname;
        private String face;
        private boolean isSelected=false;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public static class CiListBean {
        /**
         * user_id : 100252
         * nickname : 萌新用户5501
         * face : http://im-file-oss.miaorubao.com/im/img/gift_icon/1611049395_57.png
         */

        private int user_id;
        private String nickname;
        private String face;
        private boolean isSelected=false;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
