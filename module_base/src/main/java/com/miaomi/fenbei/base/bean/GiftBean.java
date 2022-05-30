package com.miaomi.fenbei.base.bean;

import com.miaomi.fenbei.base.util.DataHelper;

import java.util.List;

public  class GiftBean {


    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {


        public static class Label{
            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        private String svg_url;

        public String getSvg_url() {
            return svg_url;
        }

        public void setSvg_url(String svg_url) {
            this.svg_url = svg_url;
        }

        private int id;
        private String name;
        private String icon;
        private int price;
        private int luckPrice;
        private boolean isSelected;
        private int number = 1;
        private List<Label> label;
        private int category;
        private String noble_label;
        private boolean isLock;
        private String express;

        public String getExpress() {
            return express;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public String getNoble_label() {
            return noble_label;
        }

        public void setNoble_label(String noble_label) {
            this.noble_label = noble_label;
        }

        public boolean isLock() {
            return !DataHelper.INSTANCE.getUserInfo().getNoble_status();
        }

        public void setLock(boolean lock) {
            isLock = lock;
        }

        public int getLuckPrice() {
            return luckPrice;
        }

        public void setLuckPrice(int luckPrice) {
            this.luckPrice = luckPrice;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public List<Label> getLabel() {
            return label;
        }

        public void setLabel(List<Label> label) {
            this.label = label;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
