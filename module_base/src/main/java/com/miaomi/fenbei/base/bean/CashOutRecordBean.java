package com.miaomi.fenbei.base.bean;

import java.util.List;

public class CashOutRecordBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean{

        private String cashout_time;
        private String money;
        private String id;
        private int status;

        public String getCashout_time() {
            return cashout_time;
        }

        public void setCashout_time(String cashout_time) {
            this.cashout_time = cashout_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
}
