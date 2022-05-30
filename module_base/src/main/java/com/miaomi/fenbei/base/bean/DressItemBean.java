package com.miaomi.fenbei.base.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DressItemBean {


    private int id;
    private String name;
    private String img;
    private int sort;
    private int price;
    private String time;
    private boolean isSelected;
    private String note = "";
    private String seat_img = "";
    private String fan_name;
    private int user_decorate_id;
    private int except_time;
    private int to_user_id;
    private List<ReplaceArrBean> replaceArr;

    public String getSeat_img() {
        return seat_img;
    }

    public void setSeat_img(String seat_img) {
        this.seat_img = seat_img;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFan_name() {
        return fan_name;
    }

    public void setFan_name(String fan_name) {
        this.fan_name = fan_name;
    }

    public int getUser_decorate_id() {
        return user_decorate_id;
    }

    public void setUser_decorate_id(int user_decorate_id) {
        this.user_decorate_id = user_decorate_id;
    }

    public int getExcept_time() {
        return except_time;
    }

    public void setExcept_time(int except_time) {
        this.except_time = except_time;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }


    public List<ReplaceArrBean> getReplaceArr() {
        return replaceArr;
    }

    public void setReplaceArr(List<ReplaceArrBean> replaceArr) {
        this.replaceArr = replaceArr;
    }

//    public static class ReplaceArrBean {
//        private int type;
//        private String name;
//        private String value;
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//    }
}
