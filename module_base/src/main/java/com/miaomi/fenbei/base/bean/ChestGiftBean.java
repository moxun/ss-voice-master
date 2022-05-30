package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ChestGiftBean {




    private int balance;
    private List<RewardBean> reward;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<RewardBean> getReward() {
        return reward;
    }

    public void setReward(List<RewardBean> reward) {
        this.reward = reward;
    }

    public static class RewardBean {

        private int user_id;
        private String face = "";
        private String nickname ="";
        private int id;
        private String icon;
        private String name;
        private String svg_url;
        private int type;
        private int price;
        private int reward_type;
        private int gift_num;

        public int getGift_num() {
            return gift_num;
        }

        public void setGift_num(int gift_num) {
            this.gift_num = gift_num;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public void setNickname(String nickName) {
            this.nickname = nickName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getSvg_url() {
            return svg_url;
        }

        public void setSvg_url(String svg_url) {
            this.svg_url = svg_url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getReward_type() {
            return reward_type;
        }

        public void setReward_type(int reward_type) {
            this.reward_type = reward_type;
        }
    }
}
