package com.miaomi.fenbei.imkit.ui.viewholder.conversation;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.base.widget.SexView;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;
import com.miaomi.fenbei.imkit.R;

public class CommonConversationViewHolder extends BaseConversationViewHolder {
    private OnConversationDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnConversationDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
    private SexAndAgeView  sexTv;
    private TextView deleteTv;
    private LinearLayout rootRl;
    private TextView defriendTv;
    private LevelView gongxianIv;
    private TextView avterTv;

    public CommonConversationViewHolder(View itemView) {
        super(itemView);
        sexTv = itemView.findViewById(R.id.tv_sex);
        deleteTv = itemView.findViewById(R.id.delete_tv);
        rootRl = itemView.findViewById(R.id.rl_root);
        defriendTv = itemView.findViewById(R.id.defriend_tv);
        gongxianIv = itemView.findViewById(R.id.iv_gongxian);
        avterTv = itemView.findViewById(R.id.tv_avter);
    }


    @Override
    public void bindData(final ConversationBean bean) {
        super.bindData(bean);
        if (!TextUtils.isEmpty(bean.getFace())) {
            avterTv.setVisibility(View.GONE);
            avterIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadCircleImg(itemView.getContext(), bean.getFace(), avterIv, R.drawable.common_avter_placeholder);
        } else {
            avterTv.setVisibility(View.VISIBLE);
            avterIv.setVisibility(View.GONE);
            avterTv.setText(mBean.getNickname());
        }
        sexTv.setContent(bean.getGender() == BaseConfig.USER_INFO_GENDER_MAN,bean.getAge());
//        sexTv.setSeleted(bean.getGender() == 1);
//        sexTv.setSeleted(bean.getGender());
        rootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgManager.INSTANCE.setReadMessageForItemclik(bean.getUser_id());
                if (onDeleteListener != null){
                    onDeleteListener.onClik(bean);
                }
            }
        });
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MsgManager.INSTANCE.deleteConversationAndLocalMsgs(bean.getUser_id())) {
                    if (onDeleteListener != null) {
                        MsgManager.INSTANCE.setReadMessageForItemclik(bean.getUser_id());
                        onDeleteListener.onSuc();
                    }
                } else {
                    if (onDeleteListener != null) {
                        onDeleteListener.onFaile();
                    }
                }
            }
        });
        defriendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDeleteFd(bean.getUser_id());
                }
            }
        });
        if (bean.getWealth_level() != null) {
            gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
        }
    }

    @Override
    public String getConversationTitle() {
        return mBean.getNickname();
    }

    @Override
    public int getConversationType() {
        return CONVERSSTION_TYPE_COMMON;
    }




}
