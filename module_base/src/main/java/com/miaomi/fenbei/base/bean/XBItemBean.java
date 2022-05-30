package com.miaomi.fenbei.base.bean;

public class XBItemBean {

    /**
     * id : -8.130536715287712E7
     * face : id
     * except_time : 5.606990772990069E7
     * price : -3.702196543705743E7
     * dig_price : -7.209383754301322E7
     */

    private String id;
    private String face;
    private long except_time;
    private String price;
    private String dig_price;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public long getExcept_time() {
        return except_time;
    }

    public void setExcept_time(long except_time) {
        this.except_time = except_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDig_price() {
        return dig_price;
    }

    public void setDig_price(String dig_price) {
        this.dig_price = dig_price;
    }
}
