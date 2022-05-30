package com.miaomi.fenbei.base.bean;


import java.util.List;

public class ChatListBean {

    private String chat_host_nick;
    private int chat_host_uid;
    private int chat_online_num;
    private String chat_room_icon;
    private String chat_room_id;
    private String chat_room_name;
    private int chat_status;
    private String name;
    private int locked;
    private String label_img = "";
    private String room_topic = "";
    private String chat_host_age= "";
    private int chat_host_gender;
    private int label = 0;
    private String time = "";
    private String host_name;
    private String host_face;
    private String chat_host_face;
    private String hot_value = "";
    private List<String> up_user;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel_img() {
        return label_img;
    }

    public void setLabel_img(String label_img) {
        this.label_img = label_img;
    }

    public List<String> getUp_user() {
        return up_user;
    }

    public void setUp_user(List<String> up_user) {
        this.up_user = up_user;
    }

    public String getHot_value() {
        return hot_value;
    }

    public void setHot_value(String hot_value) {
        this.hot_value = hot_value;
    }

    public String getChat_host_face() {
        return chat_host_face;
    }

    public void setChat_host_face(String chat_host_face) {
        this.chat_host_face = chat_host_face;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_face() {
        return host_face;
    }

    public void setHost_face(String host_face) {
        this.host_face = host_face;
    }

    private String contribute ="";

    public String getContribute() {
        return contribute;
    }

    public void setContribute(String contribute) {
        this.contribute = contribute;
    }

    public String getChat_host_nick() {
        return chat_host_nick;
    }

    public void setChat_host_nick(String chat_host_nick) {
        this.chat_host_nick = chat_host_nick;
    }

    public int getChat_host_uid() {
        return chat_host_uid;
    }

    public void setChat_host_uid(int chat_host_uid) {
        this.chat_host_uid = chat_host_uid;
    }

    public int getChat_online_num() {
        return chat_online_num;
    }

    public void setChat_online_num(int chat_online_num) {
        this.chat_online_num = chat_online_num;
    }

    public String getChat_room_icon() {
        return chat_room_icon;
    }

    public void setChat_room_icon(String chat_room_icon) {
        this.chat_room_icon = chat_room_icon;
    }

    public String getChat_room_id() {
        return chat_room_id;
    }

    public void setChat_room_id(String chat_room_id) {
        this.chat_room_id = chat_room_id;
    }

    public String getChat_room_name() {
        return chat_room_name;
    }

    public void setChat_room_name(String chat_room_name) {
        this.chat_room_name = chat_room_name;
    }

    public int getChat_status() {
        return chat_status;
    }

    public void setChat_status(int chat_status) {
        this.chat_status = chat_status;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

//    public String getChat_label() {
//        return chat_label;
//    }
//
//    public void setChat_label(String chat_label) {
//        this.chat_label = chat_label;
//    }

    public String getRoom_topic() {
        return room_topic;
    }

    public void setRoom_topic(String room_topic) {
        this.room_topic = room_topic;
    }

    public String getChat_host_age() {
        return chat_host_age;
    }

    public void setChat_host_age(String chat_host_age) {
        this.chat_host_age = chat_host_age;
    }

    public int getChat_host_gender() {
        return chat_host_gender;
    }

    public void setChat_host_gender(int chat_host_gender) {
        this.chat_host_gender = chat_host_gender;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
