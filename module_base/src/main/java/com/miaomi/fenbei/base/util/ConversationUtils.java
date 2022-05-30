package com.miaomi.fenbei.base.util;

import android.content.ContentValues;

import com.miaomi.fenbei.base.bean.event.ConversationBean;

import org.litepal.LitePal;

public class ConversationUtils {
    public static void addConversation(ConversationBean bean,int level){
        bean.setLevel(level);
        bean.save();
    }
    public static void updataConversation(ConversationBean bean,int level){
        ContentValues values = new ContentValues();
        values.put("nickname", bean.getNickname());
        values.put("face", bean.getFace());
        values.put("gender", bean.getGender());
        values.put("relation", bean.getRelation());
        values.put("age", bean.getAge());
        values.put("level", level);
        LitePal.updateAll(ConversationBean.class, values, "user_id = ?",bean.getUser_id());
    }

    public static ConversationBean getConversation(Long userId){
        return LitePal.where("user_id = ?",userId.toString()).findFirst(ConversationBean.class);
    }

}
