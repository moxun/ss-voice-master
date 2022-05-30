package com.miaomi.fenbei.base.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class CharmLevelBean extends LitePalSupport {

    @Column
    private String icon;
    @Column
    private int grade;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
