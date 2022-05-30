package com.miaomi.fenbei.imkit.ui.viewholder.conversation;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.imkit.R;

public abstract class BaseConversationViewHolder extends RecyclerView.ViewHolder{
    ConversationBean mBean;
    final static int CONVERSSTION_TYPE_COMMON = 1;
    final static int CONVERSSTION_TYPE_SYSTEM = 2;
    final static int CONVERSSTION_TYPE_STRANGER = 3;
    public ImageView avterIv;
    private TextView contentTv;
    private TextView nameTv;
    private TextView timeTv;
    private TextView unReadTv;
    BaseConversationViewHolder(View itemView) {
        super(itemView);
        avterIv = itemView.findViewById(R.id.iv_avter);
        contentTv = itemView.findViewById(R.id.tv_content);
        nameTv = itemView.findViewById(R.id.tv_name);
        timeTv = itemView.findViewById(R.id.tv_time);
        unReadTv = itemView.findViewById(R.id.tv_unread);
    }
    public void bindData(ConversationBean bean){
        mBean = bean;
        if (bean.getTime() != 0){
            timeTv.setVisibility(View.VISIBLE);
            timeTv.setText(TimeUtil.longFormatTime(bean.getTime()));
        }else{
            timeTv.setVisibility(View.GONE);
        }
        if (bean.getUnReadNum()>0){
            unReadTv.setVisibility(View.VISIBLE);
            unReadTv.setText(String.valueOf(bean.getUnReadNum()));
        }else if(bean.getUnReadNum() >99){
            unReadTv.setVisibility(View.VISIBLE);
            unReadTv.setText("99+");
        }else{
            unReadTv.setVisibility(View.GONE);
        }
        contentTv.setText(getConversationContent());
        nameTv.setText(getConversationTitle());

    }
    public abstract String getConversationTitle();
    private String getConversationContent(){
        if (mBean.getMsgType() == C2CMsgBean.Empty){
            return "[空空如也]";
        }
        if (mBean.getMsgType() == C2CMsgBean.Text){
            return mBean.getContent();
        }
        if (mBean.getMsgType() == C2CMsgBean.Image){
            return "[图片]";
        }
        if (mBean.getMsgType() == C2CMsgBean.Sound){
            return "[语音]";
        }
        if (mBean.getMsgType() == C2CMsgBean.Emoji){
            return "[表情]";
        }
        if (mBean.getMsgType() == C2CMsgBean.Custom){
            return "[自定义消息]";
        }
        if (mBean.getMsgType() == C2CMsgBean.CustomGift){
            return "[礼物]";
        }
        if (mBean.getMsgType() == C2CMsgBean.CustomReply){
            return mBean.getContent();
        }
        if (mBean.getMsgType() == C2CMsgBean.CustomHello){
            return mBean.getContent();
        }
        if (mBean.getMsgType() == C2CMsgBean.CustomRedEnvelope){
            return "[红包]";
        }
        return "[未知类型消息]";
    }
    public abstract int getConversationType();
}
