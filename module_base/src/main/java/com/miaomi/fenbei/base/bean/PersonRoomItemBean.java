package com.miaomi.fenbei.base.bean;

import java.util.List;

public class PersonRoomItemBean {



    private String name;
    private int room_id;
    private int online_int;
    private String topic;
    private int user_id;
    private String face;
    private List<String> user_face;
    private String label;
    private String contribute = "";
    private String nickname = "";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContribute() {
        return contribute;
    }

    public void setContribute(String contribute) {
        this.contribute = contribute;
    }

    public class MicBean{
        private String face;
        private int gender;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }

    public String getLabel() {
        return label;
    }

    public void setType(String type) {
        this.label = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getOnline_int() {
        return online_int;
    }

    public void setOnline_int(int online_int) {
        this.online_int = online_int;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public List<String> getUser_face() {
        return user_face;
    }

    public void setUser_face(List<String> user_face) {
        this.user_face = user_face;
    }
}
