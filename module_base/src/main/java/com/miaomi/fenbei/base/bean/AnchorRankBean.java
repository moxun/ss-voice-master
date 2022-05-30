package com.miaomi.fenbei.base.bean;

import java.io.Serializable;
import java.util.List;

public class AnchorRankBean {

    /**
     * list : [{"user_id":"","sort":"排名","hot_value":"热力值","nickname":"","face":"","fans_num":""}]
     * close_time : 剩余时间
     */

    private String close_time;
    private List<AnchorFansItemBean> list;
    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public List<AnchorFansItemBean> getList() {
        return list;
    }

    public void setList(List<AnchorFansItemBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private String user_id;
        private String sort;
        private String hot_value;
        private String fans_num;
        private String nickname;
        private String face;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getHot_value() {
            return hot_value;
        }

        public void setHot_value(String hot_value) {
            this.hot_value = hot_value;
        }

        public String getFans_num() {
            return fans_num;
        }

        public void setFans_num(String fans_num) {
            this.fans_num = fans_num;
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

        //        /**
//         * user_id :
//         * sort : 排名
//         * hot_value : 热力值
//         * nickname :
//         * face :
//         * fans_num :
//         */
//
//        private String user_id;
//        private String sort;
//        private String hot_value;
//        private String nickname;
//        private String face;
//        private String fans_num;
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public String getSort() {
//            return sort;
//        }
//
//        public void setSort(String sort) {
//            this.sort = sort;
//        }
//
//        public String getHot_value() {
//            return hot_value;
//        }
//
//        public void setHot_value(String hot_value) {
//            this.hot_value = hot_value;
//        }
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public void setNickname(String nickname) {
//            this.nickname = nickname;
//        }
//
//        public String getFace() {
//            return face;
//        }
//
//        public void setFace(String face) {
//            this.face = face;
//        }
//
//        public String getFans_num() {
//            return fans_num;
//        }
//
//        public void setFans_num(String fans_num) {
//            this.fans_num = fans_num;
//        }
    }
}
