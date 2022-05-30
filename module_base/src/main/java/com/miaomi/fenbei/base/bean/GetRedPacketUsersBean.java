package com.miaomi.fenbei.base.bean;

public class GetRedPacketUsersBean {
    private String nickname;
    private String diamonds;
    private String user_id;
    private long create_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(String diamonds) {
        this.diamonds = diamonds;
    }
}
