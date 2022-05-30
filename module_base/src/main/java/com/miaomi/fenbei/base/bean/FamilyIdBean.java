package com.miaomi.fenbei.base.bean;

public class FamilyIdBean {
    private String family_id;
    private int status;
    private int role;
    private String family_face;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFamily_face() {
        return family_face;
    }

    public void setFamily_face(String family_face) {
        this.family_face = family_face;
    }
}
