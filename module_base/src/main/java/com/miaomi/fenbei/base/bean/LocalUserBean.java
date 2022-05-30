package com.miaomi.fenbei.base.bean;

import java.util.ArrayList;
import java.util.List;

public class LocalUserBean {

    private String token;
    private String sig;
    private boolean is_new_user;
    private String face = "";
    private String nickname = "";
    private int user_id = 0;
    private int good_number = 0;
    private int good_number_state = 0;
    private String mobile = "";
    private int gender = 0;
    private String birth = "";
    private String city = "";
    private String signature = "";
    private int age = 0;
    private int diamonds = 0;
    private String room_id = "";
    private int identify_status = 3;
    private LevelBean charm_level;
    private LevelBean wealth_level;
    private int user_role = 0;
    private String seat_frame = "";
    private int effects = 0;
    private int code_time = 0;
    private int room_status = 0;
    private String head_img = "";
    private String body_img = "";
    private String seat = "";
    private Boolean noble_status = false;
    private int mystery = 0;

    //0兑换密码锁已关闭  1兑换密码锁已开启
    private int exchange_pwd = 0;

    private int check_state = 0;
    private String check_face = "";
    private int fans_number = 0;
    private int authorization = 0;
    private int is_pwd = 0;
    private List<String> activity_pic = new ArrayList();
    private int rank_id;
    private String noble_name;
    private String medal = "";
    private String medal_img= "";
    private String medal_name = "";
    private String effect_svga;
    private List<ReplaceArrBean> replaceArr = new ArrayList<>();

    public String getMedal_name() {
        return medal_name;
    }

    public void setMedal_name(String medal_name) {
        this.medal_name = medal_name;
    }

    public List<ReplaceArrBean> getReplaceArr() {
        return replaceArr;
    }

    public void setReplaceArr(List<ReplaceArrBean> replaceArr) {
        this.replaceArr = replaceArr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public boolean isIs_new_user() {
        return is_new_user;
    }

    public void setIs_new_user(boolean is_new_user) {
        this.is_new_user = is_new_user;
    }



    public String getEffect_svga() {
        return effect_svga == null ? "" : effect_svga;
    }

    public void setEffect_svga(String effect_svga) {
        this.effect_svga = effect_svga;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }


    public String getMedal_img() {
        return medal_img;
    }

    public void setMedal_img(String medal_img) {
        this.medal_img = medal_img;
    }

    public String getNoble_name() {
        return noble_name;
    }

    public void setNoble_name(String noble_name) {
        this.noble_name = noble_name;
    }

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

//    public String getEffects_head() {
//        return effects_head;
//    }
//
//    public void setEffects_head(String effects_head) {
//        this.effects_head = effects_head;
//    }
//
//    public String getEffects_body() {
//        return effects_body;
//    }
//
//    public void setEffects_body(String effects_body) {
//        this.effects_body = effects_body;
//    }

    public int getExchange_pwd() {
        return exchange_pwd;
    }

    public void setExchange_pwd(int exchange_pwd) {
        this.exchange_pwd = exchange_pwd;
    }

    public String getCheck_face() {
        return check_face;
    }

    public void setCheck_face(String check_face) {
        this.check_face = check_face;
    }

    public int getCheck_state() {
        return check_state;
    }

    public void setCheck_state(int check_state) {
        this.check_state = check_state;
    }

    public int getCode_time() {
        return code_time;
    }

    public void setCode_time(int code_time) {
        this.code_time = code_time;
    }


    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public int getIdentify_status() {
        return identify_status;
    }

    public void setIdentify_status(int identify_status) {
        this.identify_status = identify_status;
    }


    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
        this.nickname = nickname.replace("\u202e", "");
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }


    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public LevelBean getCharm_level() {
        if (charm_level != null){
            return charm_level;
        }else{
            return new LevelBean();
        }
    }

    public void setCharm_level(LevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public LevelBean getWealth_level() {
        if (wealth_level != null){
            return wealth_level;
        }else{
            return new LevelBean();
        }
    }

    public void setWealth_level(LevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

    @Override
    public String toString() {
        return "LocalUserBean{" +
                "face='" + face + '\'' +
                ", nickname='" + nickname + '\'' +
                ", user_id=" + user_id +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", birth='" + birth + '\'' +
                ", city='" + city + '\'' +
                ", signature='" + signature + '\'' +
                ", age=" + age +
                ", diamonds=" + diamonds +
                ", room_id='" + room_id + '\'' +
//                ", family_id='" + family_id + '\'' +
                ", identify_status=" + identify_status +
                ", charm_level=" + charm_level +
                ", wealth_level=" + wealth_level +
                ", user_role=" + user_role +
                '}';
    }


    public int getEffects() {
        return effects;
    }

    public void setEffects(int effects) {
        this.effects = effects;
    }

    public String getSeat_frame() {
        return seat_frame;
    }

    public void setSeat_frame(String seat_frame) {
        this.seat_frame = seat_frame;
    }

    public int getGood_number_state() {
        return good_number_state;
    }

    public void setGood_number_state(int good_number_state) {
        this.good_number_state = good_number_state;
    }

    public int getFans_number() {
        return fans_number;
    }

    public void setFans_number(int fans_number) {
        this.fans_number = fans_number;
    }

    public int getRoom_status() {
        return room_status;
    }

    public void setRoom_status(int room_status) {
        this.room_status = room_status;
    }

    public int getAuthorization() {
        return authorization;
    }

    public void setAuthorization(int authorization) {
        this.authorization = authorization;
    }

    public int getIs_pwd() {
        return is_pwd;
    }

    public void setIs_pwd(int is_pwd) {
        this.is_pwd = is_pwd;
    }




    public List<String> getActivity_pic() {
        return activity_pic;
    }

    public void setActivity_pic(List<String> activity_pic) {
        this.activity_pic = activity_pic;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getBody_img() {
        return body_img;
    }

    public void setBody_img(String body_img) {
        this.body_img = body_img;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Boolean getNoble_status() {
        return noble_status;
    }

    public void setNoble_status(Boolean noble_status) {
        this.noble_status = noble_status;
    }


    public int getMystery() {
        return mystery;
    }

    public void setMystery(int mystery) {
        this.mystery = mystery;
    }


}
