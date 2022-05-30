package com.miaomi.fenbei.base.bean;

import java.util.List;

public class MortalResultBean {
    private int pai_count;
    private List<GameXXBean> list;

    public int getPai_count() {
        return pai_count;
    }

    public void setPai_count(int pai_count) {
        this.pai_count = pai_count;
    }

    public List<GameXXBean> getList() {
        return list;
    }

    public void setList(List<GameXXBean> list) {
        this.list = list;
    }
}
