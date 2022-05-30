package com.miaomi.fenbei.base.bean;

import java.util.List;

public class UserDividedBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * divided : 0.7
         * count : 0
         */

        private double divided;
        private int count;

        public double getDivided() {
            return divided;
        }

        public void setDivided(double divided) {
            this.divided = divided;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
