package com.miaomi.fenbei.imkit.ui.viewholder.conversation;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;

public class SystemViewHolder extends BaseConversationViewHolder{
    private OnConversationDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnConversationDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
    public SystemViewHolder(View itemView) {
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
                MsgManager.INSTANCE.setReadMessageForItemclik(bean.getUser_id());
                ARouter.getInstance().build("/app/systemmessage")
                        .navigation();
            }
        });
    }

    @Override
    public String getConversationTitle() {
        return "系统消息";
    }

    @Override
    public int getConversationType() {
        return CONVERSSTION_TYPE_SYSTEM;
    }
}
