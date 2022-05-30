package com.miaomi.fenbei.base.bean;


public class MeFamilyBean {


    /**
     * type : 0
     * host_info : {"family_name":"星空","family_id":300114,"member":1,"nickname":"misterg37","money_total":0,"note":"家族公告内容测试。","family_type":3,"icon":"http://im-file-oss.miaorubao.com/im_test/img/avatar/60319501617187321393.jpg","total_rank":"无排名","month_rank":"无排名"}
     */

    private int type;
    private HostInfoBean host_info;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HostInfoBean getHost_info() {
        return host_info;
    }

    public void setHost_info(HostInfoBean host_info) {
        this.host_info = host_info;
    }

    public static class HostInfoBean {
        /**
         * family_name : 星空
         * family_id : 300114
         * member : 1
         * nickname : misterg37
         * money_total : 0
         * note : 家族公告内容测试。
         * family_type : 3
         * icon : http://im-file-oss.miaorubao.com/im_test/img/avatar/60319501617187321393.jpg
         * total_rank : 无排名
         * month_rank : 无排名
         */

        private String family_name;
        private int family_id;
        private int member;
        private String nickname;
        private int money_total;
        private String note;
        private int family_type;
        private String icon;
        private String total_rank;
        private String month_rank;

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

        public int getMoney_total() {
            return money_total;
        }

        public void setMoney_total(int money_total) {
            this.money_total = money_total;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getFamily_type() {
            return family_type;
        }

        public void setFamily_type(int family_type) {
            this.family_type = family_type;
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
    }
}
