package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ShopMallItemBean {
    private String name;
    private List<DressItemBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DressItemBean> getList() {
        return list;
    }

    public void setList(List<DressItemBean> list) {
        this.list = list;
    }
}
