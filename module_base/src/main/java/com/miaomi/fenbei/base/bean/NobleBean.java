package com.miaomi.fenbei.base.bean;

import java.util.ArrayList;
import java.util.List;

public class NobleBean {


    private static final long serialVersionUID = 1L;
    private int id;
    private String name = "";
    private String icon = "";
    private String background_img = "";
    private int price;
    private int return_diamonds;
    private int renew_price;
    private int renew_return_diamonds;
    private String privilege_num = "";
    private boolean open_status;
    private String expired_time = "";
    private List<PrivilegeListBean> privilege_list = new ArrayList<>();
    private int protect_status;
    private int expired_days;
    private int wealth_value;

    public int getProtect_status() {
        return protect_status;
    }

    public void setProtect_status(int protect_status) {
        this.protect_status = protect_status;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReturn_diamonds() {
        return return_diamonds;
    }

    public void setReturn_diamonds(int return_diamonds) {
        this.return_diamonds = return_diamonds;
    }

    public int getRenew_price() {
        return renew_price;
    }

    public void setRenew_price(int renew_price) {
        this.renew_price = renew_price;
    }

    public int getRenew_return_diamonds() {
        return renew_return_diamonds;
    }

    public void setRenew_return_diamonds(int renew_return_diamonds) {
        this.renew_return_diamonds = renew_return_diamonds;
    }

    public int getExpired_days() {
        return expired_days;
    }

    public void setExpired_days(int expired_days) {
        this.expired_days = expired_days;
    }

    public int getWealth_value() {
        return wealth_value;
    }

    public void setWealth_value(int wealth_value) {
        this.wealth_value = wealth_value;
    }

    public String getPrivilege_num() {
        return privilege_num;
    }

    public void setPrivilege_num(String privilege_num) {
        this.privilege_num = privilege_num;
    }

    //是否开通贵族
    public boolean isOpen_status() {
        return open_status;
    }

    public void setOpen_status(boolean open_status) {
        this.open_status = open_status;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public List<PrivilegeListBean> getPrivilege_list() {
        return privilege_list;
    }

    public void setPrivilege_list(List<PrivilegeListBean> privilege_list) {
        this.privilege_list = privilege_list;
    }

    public static class PrivilegeListBean {


        private String icon;
        private String name;
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
