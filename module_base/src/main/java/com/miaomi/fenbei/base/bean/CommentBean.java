package com.miaomi.fenbei.base.bean;

public class CommentBean {



        /**
         * id : 15
         * friends_circle_id : 76455
         * send_user_id : 100257
         * comment : 红红火火
         * parent_id : 0
         * create_time : 1619003779
         * hf_user_name : 0
         * user_name : 萌新用户0806
         * face :
         */

        private int id;
        private int friends_circle_id;
        private int send_user_id;
        private String comment;
        private int parent_id;
        private int create_time;
        private String hf_user_name;
        private String user_name;
        private String face;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFriends_circle_id() {
            return friends_circle_id;
        }

        public void setFriends_circle_id(int friends_circle_id) {
            this.friends_circle_id = friends_circle_id;
        }

        public int getSend_user_id() {
            return send_user_id;
        }

        public void setSend_user_id(int send_user_id) {
            this.send_user_id = send_user_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getHf_user_name() {
            return hf_user_name;
        }

        public void setHf_user_name(String hf_user_name) {
            this.hf_user_name = hf_user_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

}
