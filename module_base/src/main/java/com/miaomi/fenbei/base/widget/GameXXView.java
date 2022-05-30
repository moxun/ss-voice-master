package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.bean.GameXXBean;

import androidx.annotation.Nullable;

public class GameXXView extends FrameLayout {
    private Context mContext;
//    private TextView contentTv;
    private ImageView contentIv;
//    private String[] levels = new String[]
//            {"一帆风顺","双喜临门","三阳开泰","四季平安"
//            ,"五谷丰登","六六大顺","七星高照","八方进宝"
//            ,"九九安康","十全十美","十一而税","十二金钗","十三太保"};
    public GameXXView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GameXXView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public GameXXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.base_layout_game_xx,this,true);
//        contentTv = view.findViewById(R.id.tv_content);
        contentIv = view.findViewById(R.id.iv_bg);
    }

    public void setLevel(GameXXBean bean){
        if (bean == null){
            return ;
        }
        if (bean.getLevel() > 13){
            return ;
        }
        if (bean.getType() == 0){
            contentIv.setImageResource(R.drawable.base_bg_ssq_doushou_1);
        }
        if (bean.getType() == 1){
            contentIv.setImageResource(R.drawable.base_bg_ssq_doushou_2);
        }
        if (bean.getType() == 2){
            contentIv.setImageResource(R.drawable.base_bg_ssq_doushou_3);
        }
        if (bean.getType() == 3){
            contentIv.setImageResource(R.drawable.base_bg_ssq_doushou_4);
        }
        contentIv.setImageLevel(bean.getLevel());
//        contentTv.setText(levels[bean.getLevel()]);
    }

    public void small(){

//        contentTv.setTextSize(10f);
    }

}
