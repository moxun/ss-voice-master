package com.miaomi.fenbei.base.bean;

import java.util.List;

public class PartyRoomHourBean {

    /**
     * list : [{"room_id":123466,"total":"8587828","name":"1111","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/10001741608481748570.jpg"},{"room_id":123467,"total":"493238","name":"测试房间","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/10000901608485541640.jpg","difference":8094590},{"room_id":"","total":"153737","name":"快来相亲～","icon":"http://im-file-oss.miaorubao.com/im/img/avatar/12487981614521442772.jpg","difference":339501}]
     * in_room : {"room_id":"","name":"","icon":null,"difference":0}
     */

    private InRoomBean in_room;
    private List<ListBean> list;

    public InRoomBean getIn_room() {
        return in_room;
    }

    public void setIn_room(InRoomBean in_room) {
        this.in_room = in_room;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InRoomBean {
        /**
         * room_id :
         * name :
         * icon : null
         * difference : 0
         */

        private String room_id;
        private String name;
        private Object icon;
        private int difference;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public int getDifference() {
            return difference;
        }

        public void setDifference(int difference) {
            this.difference = difference;
        }
    }

    public static class ListBean {
        /**
         * room_id : 123466
         * total : 8587828
         * name : 1111
         * icon : http://im-file-oss.miaorubao.com/im/img/avatar/10001741608481748570.jpg
         * difference : 8094590
         */

        private int room_id;
        private String total;
        private String name;
        private String icon;
        private int difference;

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public int getDifference() {
            return difference;
        }

        public void setDifference(int difference) {
            this.difference = difference;
        }
    }
}
