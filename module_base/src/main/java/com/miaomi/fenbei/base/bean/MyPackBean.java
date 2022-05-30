package com.miaomi.fenbei.base.bean;

import java.util.List;

public class MyPackBean {


    private List<GiftBean.DataBean> data;
    private String total;

    public MyPackBean(List<GiftBean.DataBean> data) {
        this.data = data;
    }


    public List<GiftBean.DataBean> getData() {
        return data;
    }

    public void setData(List<GiftBean.DataBean> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
