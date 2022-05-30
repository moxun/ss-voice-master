package com.miaomi.fenbei.base.bean.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class MusicBean extends LitePalSupport {
    long id;
    @Column
    String name;
    @Column
    String url;
    @Column
    String size;
    @Column
    String singer;
    @Column
    String uploader;
    boolean isPlaying;
    @Column(unique = true)
    long musicId;

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
