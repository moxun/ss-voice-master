package com.miaomi.fenbei.base.bean;

public class RoomlabelBean {
    private int id;
    private String name;
    private boolean isSelected;
    private String img_in;

    public String getImg_in() {
        return img_in;
    }

    public void setImg_in(String img_in) {
        this.img_in = img_in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
