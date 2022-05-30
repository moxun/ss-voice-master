package com.miaomi.fenbei.base.bean;


import java.util.List;

public class MsgHistoryBean {

    private int id;
    private int user_id;
    private int room_id;
    private String content;
    private int create_time;
    private LevelBean charm_level;
    private String face;
    private int first_sign;
    private int good_number_state;
    private int is_guard;
    private String nickname;
    private String seat_frame;
    private int user_role;
    private LevelBean wealth_level;
    private int good_number;
    private List<String> activity_pic;
    private int mystery;
    private String medal;
    private int rank_id;

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public LevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(LevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getFirst_sign() {
        return first_sign;
    }

    public void setFirst_sign(int first_sign) {
        this.first_sign = first_sign;
    }

    public int getGood_number_state() {
        return good_number_state;
    }

    public void setGood_number_state(int good_number_state) {
        this.good_number_state = good_number_state;
    }

    public int getIs_guard() {
        return is_guard;
    }

    public void setIs_guard(int is_guard) {
        this.is_guard = is_guard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSeat_frame() {
        return seat_frame;
    }

    public void setSeat_frame(String seat_frame) {
        this.seat_frame = seat_frame;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public LevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(LevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public List<String> getActivity_pic() {
        return activity_pic;
    }

    public void setActivity_pic(List<String> activity_pic) {
        this.activity_pic = activity_pic;
    }

    public int getMystery() {
        return mystery;
    }

    public void setMystery(int mystery) {
        this.mystery = mystery;
    }
}
