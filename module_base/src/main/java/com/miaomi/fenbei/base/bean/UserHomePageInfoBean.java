package com.miaomi.fenbei.base.bean;


import java.util.ArrayList;

public class UserHomePageInfoBean {


    private int user_id;
    private String nickname;
    private int gender;
    private int age;
    private String city;
    private String signature;
    private String face;
    private int fans_number;
    private int follow_status;
    private int follow_number;
    private String birth;
    private int enter_room_id;
    private ArrayList<PreviewBean> img_url_list;
    private int good_number;
    private int good_number_state;
    private int is_light_love;
    private String charm_value;
    private String wealth_value;
    private String medal;
    private String voice_url;
    private String login_time;
    private String room_name;
    private String room_icon;
    private int voice_time;
    private int voice_limit_time;
    private String lecturer;

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getFollow_number() {
        return follow_number;
    }

    public void setFollow_number(int follow_number) {
        this.follow_number = follow_number;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getVoice_limit_time() {
        return voice_limit_time;
    }

    public void setVoice_limit_time(int voice_limit_time) {
        this.voice_limit_time = voice_limit_time;
    }

    public int getVoice_time() {
        return voice_time;
    }

    public void setVoice_time(int voice_time) {
        this.voice_time = voice_time;
    }

    public String getVoice_url() {
        return voice_url;
    }

    public void setVoice_url(String voice_url) {
        this.voice_url = voice_url;
    }



    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }


    public int getGood_number_state() {
        return good_number_state;
    }

    public void setGood_number_state(int good_number_state) {
        this.good_number_state = good_number_state;
    }

    public int getIs_light_love() {
        return is_light_love;
    }

    public void setIs_light_love(int is_light_love) {
        this.is_light_love = is_light_love;
    }

    public String getCharm_value() {
        return charm_value;
    }

    public void setCharm_value(String charm_value) {
        this.charm_value = charm_value;
    }

    public String getWealth_value() {
        return wealth_value;
    }

    public void setWealth_value(String wealth_value) {
        this.wealth_value = wealth_value;
    }

    private LevelBean charm_level;
    private LevelBean wealth_level;
    public LevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(LevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public LevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(LevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

    public String getMedal() {
        return medal;
    }

    public String getRoom_icon() {
        return room_icon;
    }

    public void setRoom_icon(String room_icon) {
        this.room_icon = room_icon;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }



    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public int getEnter_room_id() {
        return enter_room_id;
    }

    public void setEnter_room_id(int enter_room_id) {
        this.enter_room_id = enter_room_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }



    public int getFans_number() {
        return fans_number;
    }

    public void setFans_number(int fans_number) {
        this.fans_number = fans_number;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
    }

    public ArrayList<PreviewBean> getImg_url_list() {
        return img_url_list;
    }

    public void setImg_url_list(ArrayList<PreviewBean> img_url_list) {
        this.img_url_list = img_url_list;
    }


}
