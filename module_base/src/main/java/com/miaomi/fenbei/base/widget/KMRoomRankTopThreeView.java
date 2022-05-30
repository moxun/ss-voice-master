package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KMRoomRankTopThreeView extends FrameLayout {

    private ImageView topRank1;
    private ImageView topRank2;
    private ImageView topRank3;
//    private ImageView toRankIv;
    private Context mContext;

    public KMRoomRankTopThreeView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public KMRoomRankTopThreeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KMRoomRankTopThreeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
//        setOrientation(LinearLayout.HORIZONTAL);
        View view = LayoutInflater.from(context).inflate(R.layout.common_room_rank_top_three, this,true);
        topRank1 = view.findViewById(R.id.iv_rank_1);
        topRank2 = view.findViewById(R.id.iv_rank_2);
        topRank3 = view.findViewById(R.id.iv_rank_3);
//        toRankIv = view.findViewById(R.id.iv_to_rank);
//        toRankIv.setVisibility(View.GONE);
    }

//    public void showToRank(){
//        toRankIv.setVisibility(View.VISIBLE);
//    }

    public void showImg1(String face1){
        if (TextUtils.isEmpty(face1)) {
            ImgUtil.INSTANCE.loadFaceIcon(mContext,R.drawable.common_avter_placeholder,topRank1);
        }else{
            ImgUtil.INSTANCE.loadFaceIcon(mContext,face1,topRank1);
        }
    }

    public void showImg2(String face2) {
        if (TextUtils.isEmpty(face2)) {
            ImgUtil.INSTANCE.loadFaceIcon(mContext,R.drawable.common_avter_placeholder,topRank2);
        }else{
            ImgUtil.INSTANCE.loadFaceIcon(mContext,face2,topRank2);
        }
    }

    public void showImg3(String face3){
        if (TextUtils.isEmpty(face3)) {
            ImgUtil.INSTANCE.loadFaceIcon(mContext,R.drawable.common_avter_placeholder,topRank3);
        }else{
            ImgUtil.INSTANCE.loadFaceIcon(mContext,face3,topRank3);
        }
    }


}
