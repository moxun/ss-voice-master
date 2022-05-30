package com.miaomi.fenbei.base.bean.event;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.bean.CharmLevelBean;
import com.miaomi.fenbei.base.bean.WealthLevelBean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class ConversationBean extends LitePalSupport implements Comparable<ConversationBean>{


//    public final static int C2C = 1;
//    public final static int System = 2;
    @Column(unique = true)
    private String user_id;
    @Column
    private String nickname;
    @Column
    private String face;
    private long time;
    @Column
    private int gender;
    private int msgType;
    private String content;
    private long unReadNum;
    @Column
    private int age;
    //1非陌生人  0陌生人
    @Column
    private int relation;

    @Column
    private int level;
    private int conversationType;
    private boolean isTop;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    @Column
    private CharmLevelBean charm_level;
    @Column
    private WealthLevelBean wealth_level;

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public CharmLevelBean getCharm_level() {
        return charm_level;
    }

    public void setCharm_level(CharmLevelBean charm_level) {
        this.charm_level = charm_level;
    }

    public WealthLevelBean getWealth_level() {
        return wealth_level;
    }

    public void setWealth_level(WealthLevelBean wealth_level) {
        this.wealth_level = wealth_level;
    }



    public int getConversationType() {
        return conversationType;
    }

    public void setConversationType(int conversationType) {
        this.conversationType = conversationType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public int compareTo(@NonNull ConversationBean o) {
        if (o.isTop()){
            return 0;
        }else{
            return (int) (o.getTime() - this.getTime());
        }
    }
}
