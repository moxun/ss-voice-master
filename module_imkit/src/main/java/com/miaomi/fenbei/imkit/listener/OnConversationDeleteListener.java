package com.miaomi.fenbei.imkit.listener;

import com.miaomi.fenbei.base.bean.event.ConversationBean;

public interface OnConversationDeleteListener {
    void onSuc();
    void onFaile();
    void onDeleteFd(String uid);
    void onClik(ConversationBean conversationBean);
}
