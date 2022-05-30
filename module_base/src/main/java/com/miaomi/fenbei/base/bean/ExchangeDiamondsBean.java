package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ExchangeDiamondsBean {
    private double earning;
    private List<GoodsBean> list;

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public class GoodsBean {
        private int id;
        private int price;
        private int diamond;
        private int send;
        private boolean isSelected = false;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getSend() {
            return send;
        }

        public void setSend(int send) {
            this.send = send;
        }
    }
}
