package com.miaomi.fenbei.base.bean;

import java.util.List;

public class ColumnInfoBean {


    /**
     * chatId : "123456"
     * content :
     * roomId : 123467
     * stationInfo : {"id":17,"time_period_start":"17:00","time_period_end":"21:00","radio_name":"小酒馆","today_topic":"一起聚聚","introduction":"无限畅饮","icon":"http://im-file-oss.miaorubao.com/im_test/img/avatar/1002521623137860468.jpg","room_id":123467}
     * opt : RADIO_COLUMN_CHANGE
     */

    private String chatId;
    private String content;
    private int roomId;
    private StationInfoBean stationInfo;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public StationInfoBean getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(StationInfoBean stationInfo) {
        this.stationInfo = stationInfo;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public static class StationInfoBean {
        /**
         * id : 17
         * time_period_start : 17:00
         * time_period_end : 21:00
         * radio_name : 小酒馆
         * today_topic : 一起聚聚
         * introduction : 无限畅饮
         * icon : http://im-file-oss.miaorubao.com/im_test/img/avatar/1002521623137860468.jpg
         * room_id : 123467
         */

        private int id;
        private String time_period_start;
        private String time_period_end;
        private String radio_name;
        private String today_topic;
        private String introduction;
        private String icon;
        private int room_id;
        private String face;
        private String nickname;
        private int fans;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime_period_start() {
            return time_period_start;
        }

        public void setTime_period_start(String time_period_start) {
            this.time_period_start = time_period_start;
        }

        public String getTime_period_end() {
            return time_period_end;
        }

        public void setTime_period_end(String time_period_end) {
            this.time_period_end = time_period_end;
        }

        public String getRadio_name() {
            return radio_name;
        }

        public void setRadio_name(String radio_name) {
            this.radio_name = radio_name;
        }

        public String getToday_topic() {
            return today_topic;
        }

        public void setToday_topic(String today_topic) {
            this.today_topic = today_topic;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }
    }
}
