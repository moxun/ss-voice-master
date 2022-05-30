package com.miaomi.fenbei.room.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.miaomi.fenbei.base.bean.MsgBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

public class TopMsgView extends ConstraintLayout {

    private TextView topMsgTv;
    private TextView topNameTv;
    private ImageView topFaceIv;
    private Context mContext;

    public TopMsgView(Context context) {
        super(context);
        init(context);
    }

    public TopMsgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopMsgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setVisibility(View.GONE);
        View view = LayoutInflater.from(context).inflate(R.layout.view_top_msg, this, true);
        topMsgTv = view.findViewById(R.id.tv_top_content);
        topNameTv = view.findViewById(R.id.tv_top_name);
        topFaceIv = view.findViewById(R.id.iv_top_face);
    }

    public void load(MsgBean bean) {
        setVisibility(View.VISIBLE);
        topMsgTv.setText(bean.getContent());
        topNameTv.setText(bean.getFromUserInfo().getNickname());
        ImgUtil.INSTANCE.loadFaceIcon(mContext, bean.getFromUserInfo().getFace(), topFaceIv);
    }

}
