package com.miaomi.fenbei.base.bean;

public class WxMiniPayBean {


    /**
     * xcx_url : {"order_no":"xxh_05111410-33125","trx_no":"100221051177326383","payee_name":"锦鹿网络","original_id":"gh_04fd80e246ac","app_id":"d3hmNTViYjQyMjIwODYwNjYx","product_name":"钻石充值","order_amout":"30.0"}
     * original_id : gh_04fd80e246ac
     */

    private XcxUrlBean xcx_url;
    private String original_id;

    public XcxUrlBean getXcx_url() {
        return xcx_url;
    }

    public void setXcx_url(XcxUrlBean xcx_url) {
        this.xcx_url = xcx_url;
    }

    public String getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(String original_id) {
        this.original_id = original_id;
    }

    public static class XcxUrlBean {
        /**
         * order_no : xxh_05111410-33125
         * trx_no : 100221051177326383
         * payee_name : 锦鹿网络
         * original_id : gh_04fd80e246ac
         * app_id : d3hmNTViYjQyMjIwODYwNjYx
         * product_name : 钻石充值
         * order_amout : 30.0
         */

        private String order_no;
        private String trx_no;
        private String payee_name;
        private String original_id;
        private String app_id;
        private String product_name;
        private String order_amout;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getTrx_no() {
            return trx_no;
        }

        public void setTrx_no(String trx_no) {
            this.trx_no = trx_no;
        }

        public String getPayee_name() {
            return payee_name;
        }

        public void setPayee_name(String payee_name) {
            this.payee_name = payee_name;
        }

        public String getOriginal_id() {
            return original_id;
        }

        public void setOriginal_id(String original_id) {
            this.original_id = original_id;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getOrder_amout() {
            return order_amout;
        }

        public void setOrder_amout(String order_amout) {
            this.order_amout = order_amout;
        }
    }
}
