package com.miaomi.fenbei.base.bean;

import java.util.List;

public class GiftInfoBean {



    private int money;
    private int total;
    private int backpack_total;
    private List<ListBean> list;

    public int getBackpack_total() {
        return backpack_total;
    }

    public void setBackpack_total(int backpack_total) {
        this.backpack_total = backpack_total;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {


        private int user_id;
        private String nickname;
        private boolean isSelected;
        private String face;
        //麦序
        private int type ;
        private int mystery;
        private int rank_id;

        public int getRank_id() {
            return rank_id;
        }

        public void setRank_id(int rank_id) {
            this.rank_id = rank_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getMystery() {
            return mystery;
        }

        public void setMystery(int mystery) {
            this.mystery = mystery;
        }
    }
}
