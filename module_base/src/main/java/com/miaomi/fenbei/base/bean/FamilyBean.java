package com.miaomi.fenbei.base.bean;

import java.util.List;

public class FamilyBean {

    /**
     * status : 1
     * list : [{"icon":"http://im-file-oss.7guoyouxi.com/im_test/img/avatar/b554f5255859743d190378d096f5f360.jpg","family_name":"G.娱乐厅","family_id":90,"money_total":"2520.00","member":4,"nickname":"手机用户"},{"icon":"http://im-file-oss.7guoyouxi.com/im/img/1546495292_495.png","family_name":"HM娱乐","family_id":53,"money_total":"2274.00","member":2,"nickname":"BlueBerry🅂"}]
     */

    private int status;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * icon : http://im-file-oss.7guoyouxi.com/im_test/img/avatar/b554f5255859743d190378d096f5f360.jpg
         * family_name : G.娱乐厅
         * family_id : 90
         * money_total : 2520.00
         * member : 4
         * nickname : 手机用户
         */
        //0 无家族  1普通家族成员 2家族管理员 3家族长 4其他家族人员5审核中
        private int family_type;
        private String icon;
        private String family_name;
        private int family_id;
        private String money_total;
        private int member;
        private String nickname;


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

        public String getMoney_total() {
            return money_total;
        }

        public void setMoney_total(String money_total) {
            this.money_total = money_total;
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
    }
}
