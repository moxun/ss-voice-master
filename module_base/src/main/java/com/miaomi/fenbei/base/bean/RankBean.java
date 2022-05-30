package com.miaomi.fenbei.base.bean;


public class RankBean {


    private LevelBean  level;

    private int distance_total1;

    public int getDistance_total1() {
        return distance_total1;
    }

    public void setDistance_total1(int distance_total1) {
        this.distance_total1 = distance_total1;
    }


    public LevelBean getLevel() {
        return level;
    }

    public void setLevel(LevelBean level) {
        this.level = level;
    }


    public void setAge(int age) {
        this.age = age;
    }

    private int age;
    private int gender;



    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }


    private String face;
    private String nickname;
    private String signature;
    private int user_id;
    private String earning_total;
    private int recharge_total;
    private int recharge_residue;
    private int total;
    private boolean room_status;
    private int room_id;
    private int nip;

    public int getNip() {
        return nip;
    }

    public void setNip(int nip) {
        this.nip = nip;
    }

    public boolean isRoom_status() {
        return room_status;
    }

    public void setRoom_status(boolean room_status) {
        this.room_status = room_status;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEarning_total() {
        return earning_total;
    }

    public void setEarning_total(String earning_total) {
        this.earning_total = earning_total;
    }

    public int getRecharge_total() {
        return recharge_total;
    }

    public void setRecharge_total(int recharge_total) {
        this.recharge_total = recharge_total;
    }

    public int getRecharge_residue() {
        return recharge_residue;
    }

    public void setRecharge_residue(int recharge_residue) {
        this.recharge_residue = recharge_residue;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

}
