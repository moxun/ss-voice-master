package com.miaomi.fenbei.imkit.ui.viewholder.conversation;

import android.view.View;

import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;
import com.miaomi.fenbei.imkit.ui.conversation.StrangerConversationActivity;

public class StrangerViewHolder extends BaseConversationViewHolder{

    private OnConversationDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnConversationDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public StrangerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(final ConversationBean bean) {
        super.bindData(bean);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null){
                    onDeleteListener.onClik(bean);
                }
                StrangerConversationActivity.start(itemView.getContext());
            }
        });
    }

    @Override
    public String getConversationTitle() {
        return "新招呼";
    }

    @Override
    public int getConversationType() {
        return CONVERSSTION_TYPE_STRANGER;
    }
}