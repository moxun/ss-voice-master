package com.miaomi.fenbei.base.bean;

public class LuckGiftNumbBean {
    public static final int SCALE_NUMB = 0;
    public static final int SCROOL_NUMB = 1;

    int count;

    int type;

    public LuckGiftNumbBean(int count, int type) {
        this.count = count;
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
