package com.miaomi.fenbei.base.bean;

import java.util.List;

public class FamilyCenterInfoBean {


    /**
     * host_info : {"family_name":"HM娱乐","family_id":53,"member":2,"nickname":"BlueBerry🅂","money_total":"2274.00","note":"1111","icon":"http://im-file-oss.7guoyouxi.com/im/img/1546495292_495.png","total_rank":"2名","month_rank":"无排名"}
     * family_info : [{"face":"http://im-file-oss.7guoyouxi.com/im/img/1546495292_495.png","nickname":"BlueBerry🅂"},{"face":"http://im-file-oss.7guoyouxi.com/im/img/1546495323_890.png","nickname":"风吹雪😀😀"},{"face":"http://im-file-oss.7guoyouxi.com/im/img/1546495379_164.png","nickname":"马赛"}]
     * room_info : [{"room_icon":"http://im-file-oss.7guoyouxi.com/im_test/img/avatar/100071551418254054.jpg","room_name":"被你您","room_nickname":"手机用户4354","room_num":1,"room_id":100020,"locked":0}]
     */

    private HostInfoBean host_info;
    private List<FamilyInfoBean> family_info;
    private List<RoomInfoBean> room_info;

    public HostInfoBean getHost_info() {
        return host_info;
    }

    public void setHost_info(HostInfoBean host_info) {
        this.host_info = host_info;
    }

    public List<FamilyInfoBean> getFamily_info() {
        return family_info;
    }

    public void setFamily_info(List<FamilyInfoBean> family_info) {
        this.family_info = family_info;
    }

    public List<RoomInfoBean> getRoom_info() {
        return room_info;
    }

    public void setRoom_info(List<RoomInfoBean> room_info) {
        this.room_info = room_info;
    }

    public static class HostInfoBean {
        /**
         * family_name : HM娱乐
         * family_id : 53
         * member : 2
         * nickname : BlueBerry🅂
         * money_total : 2274.00
         * note : 1111
         * icon : http://im-file-oss.7guoyouxi.com/im/img/1546495292_495.png
         * total_rank : 2名
         * month_rank : 无排名
         * room_num:2
         * manager_num:2
         * notice_number:6
         */
        //0 无家族  1普通家族成员 2家族管理员 3家族长 4其他家族人员
        private int family_type;
        private String family_name;
        private int family_id;
        private int member;
        private String nickname;
        private String money_total;
        private String note;
        private String icon;
        private String total_rank;
        private String month_rank;
        private int room_num;
        private int manager_num;
        private int notice_number;
        private int  join_type;
        private int  disband_type;
        private int join_status;


        public int getJoin_status() {
            return join_status;
        }

        public void setJoin_status(int join_status) {
            this.join_status = join_status;
        }

        public int getDisband_type() {
            return disband_type;
        }

        public void setDisband_type(int disband_type) {
            this.disband_type = disband_type;
        }

        public int getJoin_type() {
            return join_type;
        }

        public void setJoin_type(int join_type) {
            this.join_type = join_type;
        }

        public int getFamily_type() {
            return family_type;
        }

        public void setFamily_type(int family_type) {
            this.family_type = family_type;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public int getFamily_id() {
            return family_id;
        }

        public void setFamily_id(int family_id) {
            this.family_id = family_id;
        }

        public int getMember() {
            return member;
        }

        public void setMember(int member) {
            this.member = member;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMoney_total() {
            return money_total;
        }

        public void setMoney_total(String money_total) {
            this.money_total = money_total;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTotal_rank() {
            return total_rank;
        }

        public void setTotal_rank(String total_rank) {
            this.total_rank = total_rank;
        }

        public String getMonth_rank() {
            return month_rank;
        }

        public void setMonth_rank(String month_rank) {
            this.month_rank = month_rank;
        }

        public int getRoom_num() {
            return room_num;
        }

        public void setRoom_num(int room_num) {
            this.room_num = room_num;
        }

        public int getManager_num() {
            return manager_num;
        }

        public void setManager_num(int manager_num) {
            this.manager_num = manager_num;
        }

        public int getNotice_number() {
            return notice_number;
        }

        public void setNotice_number(int notice_number) {
            this.notice_number = notice_number;
        }
    }

    public static class FamilyInfoBean {
        /**
         * face : http://im-file-oss.7guoyouxi.com/im/img/1546495292_495.png
         * nickname : BlueBerry🅂
         */

        private int type;
        private String face;
        private String nickname;
        private int user_id;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
    }

    public static class RoomInfoBean {
        /**
         * room_icon : http://im-file-oss.7guoyouxi.com/im_test/img/avatar/100071551418254054.jpg
         * room_name : 被你您
         * room_nickname : 手机用户4354
         * room_num : 1
         * room_id : 100020
         * locked : 0
         */

        private String room_icon;
        private String room_name;
        private String room_nickname;
        private int room_num;
        private int room_id;
        private int locked;

        public String getRoom_icon() {
            return room_icon;
        }

        public void setRoom_icon(String room_icon) {
            this.room_icon = room_icon;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getRoom_nickname() {
            return room_nickname;
        }

        public void setRoom_nickname(String room_nickname) {
            this.room_nickname = room_nickname;
        }

        public int getRoom_num() {
            return room_num;
        }

        public void setRoom_num(int room_num) {
            this.room_num = room_num;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getLocked() {
            return locked;
        }

        public void setLocked(int locked) {
            this.locked = locked;
        }
    }
}
