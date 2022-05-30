package com.miaomi.fenbei.base.bean;

import java.util.List;

public class RecommandUserBean {



    private int offset;
    private int per_page;
    private int partyUserNumber;
    private List<UsersBean> users;
    private List<ChatListBean> three_room;

    public List<ChatListBean> getThree_room() {
        return three_room;
    }

    public void setThree_room(List<ChatListBean> three_room) {
        this.three_room = three_room;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPartyUserNumber() {
        return partyUserNumber;
    }

    public void setPartyUserNumber(int partyUserNumber) {
        this.partyUserNumber = partyUserNumber;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {

        private int user_id;
        private String state;
        private int login_time;
        private int gender;
        private int age;
        private String city;
        private int create_time;
        private int price;
        private String face;
        private String signature;
        private String nickname;
        private int on_state;
        private int on_login;
        private int on_create;
        private int room_id;
        private int room_type;
        private String state_text;
        private int recommend;
        private LevelBean charm_level;
        private int good_number;
        private String noble_rank;

        public String getNoble_rank() {
            return noble_rank;
        }

        public void setNoble_rank(String noble_rank) {
            this.noble_rank = noble_rank;
        }

        public LevelBean getCharm_level() {
            return charm_level;
        }

        public void setCharm_level(LevelBean charm_level) {
            this.charm_level = charm_level;
        }

        public int getGood_number() {
            return good_number;
        }

        public void setGood_number(int good_number) {
            this.good_number = good_number;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getLogin_time() {
            return login_time;
        }

        public void setLogin_time(int login_time) {
            this.login_time = login_time;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getOn_state() {
            return on_state;
        }

        public void setOn_state(int on_state) {
            this.on_state = on_state;
        }

        public int getOn_login() {
            return on_login;
        }

        public void setOn_login(int on_login) {
            this.on_login = on_login;
        }

        public int getOn_create() {
            return on_create;
        }

        public void setOn_create(int on_create) {
            this.on_create = on_create;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getRoom_type() {
            return room_type;
        }

        public void setRoom_type(int room_type) {
            this.room_type = room_type;
        }

        public String getState_text() {
            return state_text;
        }

        public void setState_text(String state_text) {
            this.state_text = state_text;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }
    }
}
