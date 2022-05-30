package com.miaomi.fenbei.base.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class FindBean implements MultiItemEntity {

    public static final int FIND_ITEM_TYPE_WB = 0;
    public static final int FIND_ITEM_TYPE_SP = 1;
    public static final int FIND_ITEM_TYPE_MP = 2;
    public static final int FIND_ITEM_TYPE_YY = 3;
    //单图
    public static final int FIND_ITEM_TYPE_DT = 4;

    private int type;
    private String id;
    private String face = "";
    private String nickname;
    private String desc = "";
    private List<String> hearts = new ArrayList<>();
    private int heart_num;
    private long create_time;
    private int age;
    private int gender;
    private String user_id = "";
    private boolean follow_status;
    private boolean heart_status;
    private boolean room_status;
    private boolean is_own;
    private String room_id = "";
    private String video_url_cover = "";

    private LevelBean charm_level;
    private int good_number;
    private String noble_rank;
    private int comment_number;
    private List<CommentBean> comment;
    public LevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(LevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public String getNoble_rank() {
        return noble_rank;
    }

    public void setNoble_rank(String noble_rank) {
        this.noble_rank = noble_rank;
    }

    public String getVideo_url_cover() {
        return video_url_cover;
    }

    public void setVideo_url_cover(String video_url_cover) {
        this.video_url_cover = video_url_cover;
    }

    public boolean isRoom_status() {
        return room_status;
    }

    public void setRoom_status(boolean room_status) {
        this.room_status = room_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<String> photos = new ArrayList<>();
    private String sound_path;
    private int sound_duration;
    private String video_url;


    public List<CommentBean> getCommentBeans() {
        return comment;
    }

    public void setCommentBeans(List<CommentBean> comment) {
        this.comment = comment;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public boolean isIs_own() {
        return is_own;
    }

    public void setIs_own(boolean is_own) {
        this.is_own = is_own;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getSound_path() {
        return sound_path;
    }

    public void setSound_path(String sound_path) {
        this.sound_path = sound_path;
    }

    public int getSound_duration() {
        return sound_duration;
    }

    public void setSound_duration(int sound_duration) {
        this.sound_duration = sound_duration;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getHearts() {
        return hearts;
    }

    public void setHearts(List<String> hearts) {
        this.hearts = hearts;
    }


    public int getHeart_num() {
        return heart_num;
    }

    public void setHeart_num(int heart_num) {
        this.heart_num = heart_num;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public FindBean(int type) {
        this.type = type;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFollow_status() {
        return follow_status;
    }

    public void setFollow_status(boolean follow_status) {
        this.follow_status = follow_status;
    }

    public boolean isHeart_status() {
        return heart_status;
    }

    public void setHeart_status(boolean heart_status) {
        this.heart_status = heart_status;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    @Override
    public int getItemType() {
        if (getType() == FindBean.FIND_ITEM_TYPE_MP){
            if (getPhotos().size() > 1){
                return FindBean.FIND_ITEM_TYPE_MP;
            }else{
                return FindBean.FIND_ITEM_TYPE_DT;
            }
        }
        return getType();
    }
}
