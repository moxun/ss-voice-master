package com.miaomi.fenbei.base.bean;

import java.util.List;

public class RedPacketRecordBean {


    private String month;
    private int money;
    private List<ListBean> list;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private int diamonds;
        private String desc;
        private String order_no;
        private int create_time;

        public int getDiamonds() {
            return diamonds;
        }

        public void setDiamonds(int diamonds) {
            this.diamonds = diamonds;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
