package com.miaomi.fenbei.base.bean;

public class BannerBean {



    private int id;
    private String title;
    private String cover;
    private String url;
    private int sort;
    private int status;
    private int mold;
//    private int create_time;
//    private int update_time;
    private String price;
    private String share_num;
    private String follow_num;

    public BannerBean() { }

    public BannerBean(String title, String url, int mold, String price, String share_num, String follow_num) {
        this.title = title;
        this.url = url;
        this.mold = mold;
        this.price = price;
        this.share_num = share_num;
        this.follow_num = follow_num;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public int getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(int create_time) {
//        this.create_time = create_time;
//    }
//
//    public int getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(int update_time) {
//        this.update_time = update_time;
//    }

    public int getMold() {
        return mold;
    }

    public void setMold(int mold) {
        this.mold = mold;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShare_num() {
        return share_num;
    }

    public void setShare_num(String share_num) {
        this.share_num = share_num;
    }

    public String getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(String follow_num) {
        this.follow_num = follow_num;
    }
}
