package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DanMuView extends FrameLayout {

    private ImageView faceIv;
    private Context mContext;
    private TextView contentTv;
    private String[] colors = new String[]{"#B955CC","#F7B500","#32C5FF","#44D7B6","#FA6400","#E02020","#3C3C3C"};

    public DanMuView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DanMuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DanMuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_danmu,this,true);
        view.setPadding(DensityUtil.dp2px(2f), DensityUtil.dp2px(5f), DensityUtil.dp2px(2f), DensityUtil.dp2px(5f));
        faceIv = view.findViewById(R.id.iv_face);
        contentTv = view.findViewById(R.id.tv_content);
    }

    public void loadFace(String url){
        ImgUtil.INSTANCE.loadFaceIcon(mContext,url,faceIv);
    }

    public void setContent(String content){
        contentTv.setText(content);
    }

    public void setColor(int index){
        index = index % 7;
        contentTv.setTextColor(Color.parseColor(colors[index]));
    }
}
