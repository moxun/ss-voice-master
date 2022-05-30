package com.miaomi.fenbei.base.bean;

import java.util.List;

public class HeartBean {
    private boolean heart_status;
    private List<String> hearts;

    public List<String> getHearts() {
        return hearts;
    }

    public void setHearts(List<String> hearts) {
        this.hearts = hearts;
    }

    public boolean isHeart_status() {
        return heart_status;
    }

    public void setHeart_status(boolean heart_status) {
        this.heart_status = heart_status;
    }
}
