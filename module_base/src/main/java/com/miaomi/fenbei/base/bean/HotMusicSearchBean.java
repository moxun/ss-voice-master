package com.miaomi.fenbei.base.bean;

import com.miaomi.fenbei.base.bean.db.MusicBean;

import java.util.List;

public class HotMusicSearchBean {

    private List<MusicBean> list;

    public List<MusicBean> getList() {
        return list;
    }

    public void setList(List<MusicBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private double id;
        private String name;
        private String url;
        private String size;
        private String singer;
        private String uploader;
//        Unknown host 'dl.bintray.com'. You may need to adjust the proxy settings in Gradle.
        public double getId() {
            return id;
        }

        public void setId(double id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getUploader() {
            return uploader;
        }

        public void setUploader(String uploader) {
            this.uploader = uploader;
        }
    }
}
