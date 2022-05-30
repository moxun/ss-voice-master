package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RankTopView1 extends FrameLayout {

    public final static int RANK_TYPE_ML = 0;
    public final static int RANK_TYPE_GX = 1;

    private Context mContext;
    private TextView nameTv;
    private ImageView avterIv;
    private LevelView levelView;
    private SexView sexTv;
    private TextView contentTv;
    private ImageView mcIv;
    private FrameLayout rankTopFl;


    public RankTopView1(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RankTopView1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public RankTopView1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    private void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_room_rank_top,this,true);
        nameTv = view.findViewById(R.id.tv_name);
        avterIv = view.findViewById(R.id.iv_avter);
        levelView = view.findViewById(R.id.iv_level);
        sexTv = view.findViewById(R.id.tv_sex);
        contentTv = view.findViewById(R.id.tv_content);
        mcIv = view.findViewById(R.id.iv_avter_rank);
        rankTopFl = view.findViewById(R.id.fl_rank_top);
        contentTv.setVisibility(INVISIBLE);
    }
    public void setImg(String url){
        ImgUtil.INSTANCE.loadFaceIcon(mContext,url,avterIv);
    }

    public void setContent(String content){
        contentTv.setVisibility(VISIBLE);
        contentTv.setText(content);
    }

    public void setRankType(int type){
        if(type==0){
            contentTv.setSelected(false);
        }else{
            contentTv.setSelected(true);
        }
        rankTopFl.setSelected(type == 2);
    }
    public void setName(String name){
        nameTv.setText(name);
    }
    public void setLevel(int level,int levelType){
        if (levelType == RANK_TYPE_ML){
            levelView.setCharmLevel(level);
        }else{
            levelView.setWealthLevel(level);
        }
    }

    public void setCharmLevel(int level){
//        levelView.setCharmLevel(level);
    }
    public void setWealthLevel(int level){
//        levelView.setWealthLevel(level);
    }
    public void setSex(int sex){
        sexTv.setSeleted(sex);
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
