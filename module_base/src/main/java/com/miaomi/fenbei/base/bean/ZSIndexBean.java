package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ZSIndexBean {

    /**
     * is_display_prize : -4.224932950428073E7
     * message : [{"nickname":"incididunt aliquip in","name":"amet velit sed pariatur commodo","count":9038364.779220581},{"nickname":"elit id quis sunt","name":"magna pariatur","count":-3.004718336295013E7},{"nickname":"","name":"Ut nisi ad tempor anim","count":-7.359767751981544E7}]
     * mature : 7695319.774701253
     * expired_at : laboris nulla nisi magna exercitation
     * progress : -9.862330898496054E7
     * recharge_residue : -4.497535294122783E7
     * water : 1.2649533258603409E7
     * "raindew": 0,
     * rule : in amet est tempor ex
     */

    private int is_display_prize;
    private int mature;
    private long expired_at;
    private double progress;
    private double recharge_residue;
    private String water;
    private String raindew;
    private String rule;
    private List<MessageBean> message;

    public int getIs_display_prize() {
        return is_display_prize;
    }

    public void setIs_display_prize(int is_display_prize) {
        this.is_display_prize = is_display_prize;
    }

    public int getMature() {
        return mature;
    }

    public void setMature(int mature) {
        this.mature = mature;
    }

    public long getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(long expired_at) {
        this.expired_at = expired_at;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getRecharge_residue() {
        return recharge_residue;
    }

    public void setRecharge_residue(double recharge_residue) {
        this.recharge_residue = recharge_residue;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getRaindew() {
        return raindew;
    }

    public void setRaindew(String raindew) {
        this.raindew = raindew;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * nickname : incididunt aliquip in
         * name : amet velit sed pariatur commodo
         * count : 9038364.779220581
         */

        private String nickname;
        private String name;
        private int count;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
