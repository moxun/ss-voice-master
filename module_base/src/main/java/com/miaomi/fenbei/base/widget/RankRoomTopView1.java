package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;


public class RankRoomTopView1 extends FrameLayout {

    public final static int RANK_TYPE_ML = 0;
    public final static int RANK_TYPE_GX = 1;

    private Context mContext;
    private TextView nameTv;
    private ImageView avterIv;


    private ImageView mcIv;
    private FrameLayout rankTopFl;
     private TextView totalTv;

    public RankRoomTopView1(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RankRoomTopView1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public RankRoomTopView1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_new_room_rank_top,this,true);
        nameTv = view.findViewById(R.id.tv_name);
        avterIv = view.findViewById(R.id.iv_avter);
        mcIv = view.findViewById(R.id.iv_avter_rank);
        rankTopFl = view.findViewById(R.id.fl_rank_top);
        totalTv= view.findViewById(R.id.tv_total);

    }
    public void setImg(String url){
        ImgUtil.INSTANCE.loadRoundImg(mContext,url,avterIv,8f,-1);
    }

    public void setTotal(String total){
        totalTv.setText(total);
        totalTv.setVisibility(View.INVISIBLE);
    }

    public void setName(String name){
        nameTv.setText(name);
    }



    public void setRankTop(int mc){
//        if (mc == 1){
//            mcIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.common_icon_rank_top_1));
//        }
//        if (mc == 2){
//            mcIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.common_icon_rank_top_2));
//        }
//        if (mc == 3){
//            mcIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.common_icon_rank_top_3));
//        }
    }
}
