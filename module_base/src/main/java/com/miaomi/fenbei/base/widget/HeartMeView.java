package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeartMeView extends FrameLayout {
    private Context mContext;
    private ImageView avatar1;
    private ImageView avatar2;
    private ImageView avatar3;
    private ImageView moreAvatar;
    public HeartMeView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public HeartMeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public HeartMeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.base_layout_heart_view,this,true);
        avatar1 = findViewById(R.id.iv_face_1);
        avatar2 = findViewById(R.id.iv_face_2);
        avatar3 = findViewById(R.id.iv_face_3);
        moreAvatar = findViewById(R.id.iv_face_more);
    }


    public void setContent(List<String> strings){
        avatar1.setVisibility(View.GONE);
        avatar2.setVisibility(View.GONE);
        avatar3.setVisibility(View.GONE);
        moreAvatar.setVisibility(View.GONE);
        if (strings.size() >= 1){
            avatar1.setVisibility(VISIBLE);
            ImgUtil.INSTANCE.loadFaceIcon(mContext,strings.get(0),avatar1);
        }
        if (strings.size() >= 2){
            avatar2.setVisibility(VISIBLE);
            ImgUtil.INSTANCE.loadFaceIcon(mContext,strings.get(1),avatar2);
        }
        if (strings.size() >= 3){
            avatar3.setVisibility(VISIBLE);
            ImgUtil.INSTANCE.loadFaceIcon(mContext,strings.get(2),avatar3);
        }
        if (strings.size() > 3){
            moreAvatar.setVisibility(VISIBLE);
        }
    }
}
