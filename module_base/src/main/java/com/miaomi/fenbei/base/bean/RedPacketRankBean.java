package com.miaomi.fenbei.base.bean;

import java.util.List;

public class RedPacketRankBean {

    private int split_count;
    private int diamonds;
    private int grab_count;
    private List<ItemsBean> items;

    public int getSplit_count() {
        return split_count;
    }

    public void setSplit_count(int split_count) {
        this.split_count = split_count;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getGrab_count() {
        return grab_count;
    }

    public void setGrab_count(int grab_count) {
        this.grab_count = grab_count;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {

        private int id;
        private int collection_id;
        private int user_id;
        private int room_id;
        private int grab_id;
        private int diamonds;
        private int create_time;
        private int grab_time;
        private int status;
        private String nickname;
        private String face;
        private int best;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCollection_id() {
            return collection_id;
        }

        public void setCollection_id(int collection_id) {
            this.collection_id = collection_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getGrab_id() {
            return grab_id;
        }

        public void setGrab_id(int grab_id) {
            this.grab_id = grab_id;
        }

        public int getDiamonds() {
            return diamonds;
        }

        public void setDiamonds(int diamonds) {
            this.diamonds = diamonds;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getGrab_time() {
            return grab_time;
        }

        public void setGrab_time(int grab_time) {
            this.grab_time = grab_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getBest() {
            return best;
        }

        public void setBest(int best) {
            this.best = best;
        }
    }
}
