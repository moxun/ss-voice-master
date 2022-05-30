package com.miaomi.fenbei.base.bean;

public class XBHideGiftBean {

    /**
     * price : 2.1325041989296347E7
     * icon : enim magna do sunt
     */

    private String price;
    private String icon;
    private String id;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
