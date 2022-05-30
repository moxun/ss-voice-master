package com.miaomi.fenbei.base.bean;

import java.util.List;

public class RoomListBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"user_id":100295,"name":"速速","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/3fcb418238490f7a7e1588b03992e4cb.jpg","good_number":0,"id":123501,"status":0,"locked":0},{"user_id":100270,"name":"欢迎欢迎","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/4ffd246f636f8f51a706b12907d43879.jpg","good_number":0,"id":123478,"status":0,"locked":0},{"user_id":100263,"name":"风中的乔治","icon":"http://im-file-oss.miaorubao.com/im_test/img/avatar/1002631619507042449.jpg","good_number":0,"id":123475,"status":0,"locked":0},{"user_id":100261,"name":"欢迎欢迎👏","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/88c35c2565b833b85dde2280e345f8f6.jpg","good_number":0,"id":123476,"status":1,"locked":0},{"user_id":100251,"name":"1111","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/10001741608481748570.jpg","good_number":1,"id":123466,"status":0,"locked":0},{"user_id":100252,"name":"测试房间","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/10000901608485541640.jpg","good_number":0,"id":123467,"status":0,"locked":0},{"user_id":100268,"name":"测试测试","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/458f4349035632616293bdb620f44011.jpg","good_number":0,"id":123477,"status":0,"locked":0},{"user_id":100262,"name":"快来相亲～","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/1510cfdba4e1017173fedb74102e608c.jpg","good_number":0,"id":123474,"status":0,"locked":0}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 100295
         * name : 速速
         * icon : http://im-file-oss.miaorubao.com/im/img/avatar/3fcb418238490f7a7e1588b03992e4cb.jpg
         * good_number : 0
         * id : 123501
         * status : 0
         * locked : 0
         */

        private int user_id;
        private String name;
        private String icon;
        private int good_number;
        private int id;
        private int status;
        private int locked;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getGood_number() {
            return good_number;
        }

        public void setGood_number(int good_number) {
            this.good_number = good_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLocked() {
            return locked;
        }

        public void setLocked(int locked) {
            this.locked = locked;
        }
    }
}
