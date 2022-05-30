package com.miaomi.fenbei.base.bean;

import java.util.List;

public class LightUpBean  {


    /**
     * chatId : 123467
     * content :
     * fromUserInfo : {"age":0,"city":"","face":"","gender":0,"mic_speaking":false,"nickname":"","recharge_residue":0,"speak":0,"status":0,"type":0,"user_id":"100265"}
     * have_people : [0,0,0,0,0,0]
     * bright : [1,0,0,0,0,0]
     * giftId : 0
     * opt : XIANGQING_BRIGHT
     */

    private String chatId;
    private String content;
    private FromUserInfoBean fromUserInfo;
    private List<Integer> have_people;
    private List<Integer> bright;
    private int giftId;
    private String opt;
    private int step;
    private int lights_music;


    public int getLights_music() {
        return lights_music;
    }

    public void setLights_music(int lights_music) {
        this.lights_music = lights_music;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FromUserInfoBean getFromUserInfo() {
        return fromUserInfo;
    }

    public void setFromUserInfo(FromUserInfoBean fromUserInfo) {
        this.fromUserInfo = fromUserInfo;
    }

    public List<Integer> getHave_people() {
        return have_people;
    }

    public void setHave_people(List<Integer> have_people) {
        this.have_people = have_people;
    }

    public List<Integer> getBright() {
        return bright;
    }

    public void setBright(List<Integer> bright) {
        this.bright = bright;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public static class FromUserInfoBean {
        /**
         * age : 0
         * city :
         * face :
         * gender : 0
         * mic_speaking : false
         * nickname :
         * recharge_residue : 0
         * speak : 0
         * status : 0
         * type : 0
         * user_id : 100265
         */

        private int age;
        private String city;
        private String face;
        private int gender;
        private boolean mic_speaking;
        private String nickname;
        private int recharge_residue;
        private int speak;
        private int status;
        private int type;
        private String user_id;

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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public boolean isMic_speaking() {
            return mic_speaking;
        }

        public void setMic_speaking(boolean mic_speaking) {
            this.mic_speaking = mic_speaking;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getRecharge_residue() {
            return recharge_residue;
        }

        public void setRecharge_residue(int recharge_residue) {
            this.recharge_residue = recharge_residue;
        }

        public int getSpeak() {
            return speak;
        }

        public void setSpeak(int speak) {
            this.speak = speak;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
