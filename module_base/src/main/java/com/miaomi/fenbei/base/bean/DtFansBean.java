package com.miaomi.fenbei.base.bean;

import java.util.List;

public class DtFansBean {

    private String zhubo_nickname;

    private List<JoinTheFanGroupBean> img;
    private String fan_type;
    private int is_fan;
    private String shen;
    private List<ConfigBean> config;
    private int is_attention;
    private int is_room_chat;
    private int is_send_gift;
    private int is_listen;

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public int getIs_room_chat() {
        return is_room_chat;
    }

    public void setIs_room_chat(int is_room_chat) {
        this.is_room_chat = is_room_chat;
    }

    public int getIs_send_gift() {
        return is_send_gift;
    }

    public void setIs_send_gift(int is_send_gift) {
        this.is_send_gift = is_send_gift;
    }

    public int getIs_listen() {
        return is_listen;
    }

    public void setIs_listen(int is_listen) {
        this.is_listen = is_listen;
    }

    public List<JoinTheFanGroupBean> getImg() {
        return img;
    }

    public void setImg(List<JoinTheFanGroupBean> img) {
        this.img = img;
    }

    //        public String getMedal_img() {
//            return medal_img;
//        }
//
//        public void setMedal_img(String medal_img) {
//            this.medal_img = medal_img;
//        }
//
//        public String getDecorate_img() {
//            return decorate_img;
//        }
//
//        public void setDecorate_img(String decorate_img) {
//            this.decorate_img = decorate_img;
//        }
//
//        public String getPosition_img() {
//            return position_img;
//        }
//
//        public void setPosition_img(String position_img) {
//            this.position_img = position_img;
//        }

    public String getFan_type() {
        return fan_type;
    }

    public void setFan_type(String fan_type) {
        this.fan_type = fan_type;
    }

    public int getIs_fan() {
        return is_fan;
    }

    public void setIs_fan(int is_fan) {
        this.is_fan = is_fan;
    }

    public String getShen() {
        return shen;
    }

    public void setShen(String shen) {
        this.shen = shen;
    }

    public List<ConfigBean> getConfig() {
        return config;
    }

    public void setConfig(List<ConfigBean> config) {
        this.config = config;
    }

    public static class ConfigBean {
        private int day;
        private int zuan;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getZuan() {
            return zuan;
        }

        public void setZuan(int zuan) {
            this.zuan = zuan;
        }
    }

    public String getZhubo_nickname() {
        return zhubo_nickname;
    }

    public void setZhubo_nickname(String zhubo_nickname) {
        this.zhubo_nickname = zhubo_nickname;
    }

}
