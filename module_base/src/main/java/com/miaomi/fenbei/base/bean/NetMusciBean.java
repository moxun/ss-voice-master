package com.miaomi.fenbei.base.bean;

import com.miaomi.fenbei.base.bean.db.MusicBean;

import java.util.List;

public class NetMusciBean {


    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {


        private double total;
        private double per_page;
        private double current_page;
        private double last_page;
        private List<MusicBean> data;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getPer_page() {
            return per_page;
        }

        public void setPer_page(double per_page) {
            this.per_page = per_page;
        }

        public double getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(double current_page) {
            this.current_page = current_page;
        }

        public double getLast_page() {
            return last_page;
        }

        public void setLast_page(double last_page) {
            this.last_page = last_page;
        }

        public List<MusicBean> getData() {
            return data;
        }

        public void setData(List<MusicBean> data) {
            this.data = data;
        }
    }
}
