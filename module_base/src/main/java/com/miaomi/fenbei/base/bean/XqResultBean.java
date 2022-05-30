package com.miaomi.fenbei.base.bean;

public class XqResultBean {


    /**
     * chatId : 123467
     * content :
     * fromUserInfo : {"age":0,"city":"","face":"","gender":0,"mic_speaking":false,"nickname":"","recharge_residue":0,"speak":0,"status":0,"type":0,"user_id":100252}
     * xiangqing_head : {"zhu":"http://im-file-oss.miaorubao.com/im/img/fenbei_default_face.png","ci":"http://im-file-oss.miaorubao.com/im/img/avatar/1002721620288531535.jpg"}
     * hold_hands_count : 31
     * blind_data_result_id : 1
     * giftId : 0
     * opt : XIANGQING_SUCCESS
     */

    private String chatId;
    private String content;
    private FromUserInfoBean fromUserInfo;
    private XiangqingHeadBean xiangqing_head;
    private int hold_hands_count;
    private int blind_data_result_id;
    private int giftId;
    private String opt;

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

    public XiangqingHeadBean getXiangqing_head() {
        return xiangqing_head;
    }

    public void setXiangqing_head(XiangqingHeadBean xiangqing_head) {
        this.xiangqing_head = xiangqing_head;
    }

    public int getHold_hands_count() {
        return hold_hands_count;
    }

    public void setHold_hands_count(int hold_hands_count) {
        this.hold_hands_count = hold_hands_count;
    }

    public int getBlind_data_result_id() {
        return blind_data_result_id;
    }

    public void setBlind_data_result_id(int blind_data_result_id) {
        this.blind_data_result_id = blind_data_result_id;
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
         * user_id : 100252
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
        private int user_id;

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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public static class XiangqingHeadBean {
        /**
         * zhu : http://im-file-oss.miaorubao.com/im/img/fenbei_default_face.png
         * ci : http://im-file-oss.miaorubao.com/im/img/avatar/1002721620288531535.jpg
         */

        private String zhu;
        private String ci;

        public String getZhu() {
            return zhu;
        }

        public void setZhu(String zhu) {
            this.zhu = zhu;
        }

        public String getCi() {
            return ci;
        }

        public void setCi(String ci) {
            this.ci = ci;
        }
    }
}
