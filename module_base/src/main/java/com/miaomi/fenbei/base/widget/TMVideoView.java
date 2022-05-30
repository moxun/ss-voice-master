package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TMVideoView extends FrameLayout {
    private Context mContext;
    private RoundImageView converView;
    private ImageView closeIv;
    public TMVideoView(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TMVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TMVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.base_layout_video_view,this,true);
        converView = findViewById(R.id.iv_conver);
        closeIv = findViewById(R.id.iv_close);
    }

    public void loadImg(String url){
        ImgUtil.INSTANCE.loadImg(mContext,url,converView, R.drawable.base_placeholder_photo_big);
    }

    public void asImg(){
        closeIv.setVisibility(View.GONE);
    }

    public void setCloseLisener(OnClickListener onClickListener){
        closeIv.setVisibility(View.VISIBLE);
        closeIv.setOnClickListener(onClickListener);
    }
}
