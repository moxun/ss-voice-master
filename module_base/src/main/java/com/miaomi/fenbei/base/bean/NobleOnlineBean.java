package com.miaomi.fenbei.base.bean;

public class NobleOnlineBean {
    private String fromUserAvtar = "";
    private String fromUserNickname = "";
    private int rankId;

    public NobleOnlineBean(String fromUserAvtar, String fromUserNickname, int rankId) {
        this.fromUserAvtar = fromUserAvtar;
        this.fromUserNickname = fromUserNickname;
        this.rankId = rankId;
    }

    public String getFromUserAvtar() {
        return fromUserAvtar;
    }

    public void setFromUserAvtar(String fromUserAvtar) {
        this.fromUserAvtar = fromUserAvtar;
    }

    public String getFromUserNickname() {
        return fromUserNickname;
    }

    public void setFromUserNickname(String fromUserNickname) {
        this.fromUserNickname = fromUserNickname;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }
}
