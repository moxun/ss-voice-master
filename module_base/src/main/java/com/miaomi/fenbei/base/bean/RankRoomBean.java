package com.miaomi.fenbei.base.bean;


import java.util.List;

public class RankRoomBean {


        /**
         * data : [{"earning_total":110946,"room_id":224315,"nickname":"☞迈阿密☜质感女神🌈","face":"http://im-file-oss.miaorubao.com/im/img/avatar/1616410065_47.jpg","difference":0},{"earning_total":8141,"room_id":224370,"nickname":"China 妊兽/女友新厅开业","face":"http://im-file-oss.miaorubao.com/im/img/avatar/60305331616570925901.jpg","difference":102805},{"earning_total":4216,"room_id":224361,"nickname":"Zg.朝歌女友💋等君为我而来","face":"http://im-file-oss.miaorubao.com/im/img/avatar/60304171616306528024.jpg","difference":3925},{"earning_total":1155,"room_id":224231,"nickname":"星熠女友✈️全麦优质","face":"http://im-file-oss.miaorubao.com/im/img/avatar/60273021616139156436.jpg","difference":3061},{"earning_total":703,"room_id":223982,"nickname":"慢热吖","face":"http://im-file-oss.miaorubao.com/im/img/avatar/60007831616213898109.jpg","difference":452},{"earning_total":441,"room_id":223964,"nickname":"123","face":"http://im-file-oss.miaorubao.com/im/img/gift_icon/1611049395_57.png","difference":262},{"earning_total":159,"room_id":224415,"nickname":"","face":null,"difference":282},{"earning_total":117,"room_id":224290,"nickname":"11","face":"http://im-file-oss.miaorubao.com/im/img/avatar/60284031615283766739.jpg","difference":42}]
         * my : {"id":8,"have_room":0,"up_number":0,"up":0,"difference":42}
         */

        private List<DataBean> data;
        private MyBean my;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public MyBean getMy() {
            return my;
        }

        public void setMy(MyBean my) {
            this.my = my;
        }

        public static class MyBean {
            /**
             * id : 8
             * have_room : 0
             * up_number : 0
             * up : 0
             * difference : 42
             */

            private int id;
            private int have_room;
            private int up_number;
            private int up;
            private int difference;
            private String face;
            private int earning_total;



            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getHave_room() {
                return have_room;
            }

            public void setHave_room(int have_room) {
                this.have_room = have_room;
            }

            public int getUp_number() {
                return up_number;
            }

            public void setUp_number(int up_number) {
                this.up_number = up_number;
            }

            public int getUp() {
                return up;
            }

            public void setUp(int up) {
                this.up = up;
            }

            public int getDifference() {
                return difference;
            }

            public void setDifference(int difference) {
                this.difference = difference;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getEarning_total() {
                return earning_total;
            }

            public void setEarning_total(int earning_total) {
                this.earning_total = earning_total;
            }
        }

        public static class DataBean {
            /**
             * earning_total : 110946
             * room_id : 224315
             * nickname : ☞迈阿密☜质感女神🌈
             * face : http://im-file-oss.miaorubao.com/im/img/avatar/1616410065_47.jpg
             * difference : 0
             */

            private int earning_total;
            private int room_id;
            private String nickname;
            private String face;
            private int difference;
            private long distance_total1;

            public long getDistance_total1() {
                return distance_total1;
            }

            public void setDistance_total1(long distance_total1) {
                this.distance_total1 = distance_total1;
            }

            public int getEarning_total() {
                return earning_total;
            }

            public void setEarning_total(int earning_total) {
                this.earning_total = earning_total;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getDifference() {
                return difference;
            }

            public void setDifference(int difference) {
                this.difference = difference;
            }
        }

}
