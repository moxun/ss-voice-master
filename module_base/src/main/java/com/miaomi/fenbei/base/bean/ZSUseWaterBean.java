package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ZSUseWaterBean {
    private List<ZSRewardBean> prize;
    private int mature;
    private String expired_at;
    private double progress;

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getMature() {
        return mature;
    }

    public void setMature(int mature) {
        this.mature = mature;
    }

    public List<ZSRewardBean> getPrize() {
        return prize;
    }

    public void setPrize(List<ZSRewardBean> prize) {
        this.prize = prize;
    }
}
