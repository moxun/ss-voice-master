package com.miaomi.fenbei.base.bean;


import java.util.ArrayList;

public class HttpPageDataBean<T> {
    private ArrayList<T> list;
    private PageBean page;

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }
    public boolean isEnd(){
        return page.current == page.last;
    }

    class PageBean{
        private int current;
        private int last;


        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getLast() {
            return last;
        }

        public void setLast(int last) {
            this.last = last;
        }

        @Override
        public String toString() {
            return "PageBean{" +
                    "current=" + current +
                    ", last=" + last +
                    '}';
        }
    }
}
