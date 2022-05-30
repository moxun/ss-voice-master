package com.miaomi.fenbei.base.bean;

public class RandomRoomBean {

    /**
     * user_id : 100262
     * name : 快来相亲～
     * icon : http://im-file-oss.miaorubao.com/im/img/avatar/1510cfdba4e1017173fedb74102e608c.jpg
     * good_number : 0
     * id : 123474
     * status : 0
     * locked : 0
     */

    private int user_id;
    private String name;
    private String icon;
    private int good_number;
    private int id;
    private int status;
    private int locked;
    private String contribute;

    public String getContribute() {
        return contribute;
    }

    public void setContribute(String contribute) {
        this.contribute = contribute;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}
