package com.miaomi.fenbei.base.bean;

import java.util.List;

public class GiftWallBean {

        /**
         * count : {"has_gift":4,"total_gift":127,"start_count":0}
         * list : [{"gift_id":152,"number":1,"name":"亚特兰蒂斯","icon":"http://im-file-oss.miaorubao.com/im/img/gift_icon/1615024488_29.png","start_num":0},{"gift_id":151,"number":1,"name":"七彩泡泡","icon":"http://im-file-oss.miaorubao.com/im/img/gift_icon/1615024437_666.png","start_num":0},{"gift_id":153,"number":1,"name":"11111","icon":"http://im-file-oss.miaorubao.com/im/img/gift_icon/1615548398_340.png","start_num":0},{"gift_id":150,"number":1,"name":"女神王冠","icon":"http://im-file-oss.miaorubao.com/im/img/gift_icon/1615024407_478.png","start_num":0}]
         */

        private CountBean count;
        private List<ListBean> list;

        public CountBean getCount() {
            return count;
        }

        public void setCount(CountBean count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CountBean {
            /**
             * has_gift : 4
             * total_gift : 127
             * start_count : 0
             */

            private int has_gift;
            private int total_gift;
            private int start_count;

            public int getHas_gift() {
                return has_gift;
            }

            public void setHas_gift(int has_gift) {
                this.has_gift = has_gift;
            }

            public int getTotal_gift() {
                return total_gift;
            }

            public void setTotal_gift(int total_gift) {
                this.total_gift = total_gift;
            }

            public int getStart_count() {
                return start_count;
            }

            public void setStart_count(int start_count) {
                this.start_count = start_count;
            }
        }

        public static class ListBean {
            /**
             * gift_id : 152
             * number : 1
             * name : 亚特兰蒂斯
             * icon : http://im-file-oss.miaorubao.com/im/img/gift_icon/1615024488_29.png
             * start_num : 0
             */

            private int gift_id;
            private int number;
            private String name;
            private String icon;
            private int start_num;

            public int getGift_id() {
                return gift_id;
            }

            public void setGift_id(int gift_id) {
                this.gift_id = gift_id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
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

            public int getStart_num() {
                return start_num;
            }

            public void setStart_num(int start_num) {
                this.start_num = start_num;
            }
        }



//    private int gift_id;
//    private String name;
//    private String icon;
//    private String svg_url;
//    private String number;
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//    public int getGift_id() {
//        return gift_id;
//    }
//
//    public void setGift_id(int gift_id) {
//        this.gift_id = gift_id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }
//
//    public String getSvg_url() {
//        return svg_url;
//    }
//
//    public void setSvg_url(String svg_url) {
//        this.svg_url = svg_url;
//    }
}
