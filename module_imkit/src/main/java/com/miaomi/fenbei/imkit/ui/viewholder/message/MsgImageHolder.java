package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.imkit.R;

import java.util.ArrayList;

public class MsgImageHolder extends MsgBaseHolder{

    private ImageView otherTv;
    private ImageView meTv;
    public MsgImageHolder(View itemView) {
        super(itemView);
        otherLL = itemView.findViewById(R.id.ll_other);
        otherTv = itemView.findViewById(R.id.tv_other);
        meLL = itemView.findViewById(R.id.ll_me);
        meTv = itemView.findViewById(R.id.tv_me);
    }


    @Override
    void bindChildData(final C2CMsgBean bean) {
//        otherTv.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ToastUtil.INSTANCE.suc(itemView.getContext(),"长按");
//                return true;
//            }
//        });
        meTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(meTv,bean);
                }
                return true;
            }
        });
        otherTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(otherTv,bean);
                }
                return true;
            }
        });
        otherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgList = new ArrayList<>();
                imgList.add(bean.getOriginalImg());
                ARouter.getInstance().build("/app/bigimg")
                        .withStringArrayList("imgUrl_list",imgList)
                        .withBoolean("is_click",false)
                        .navigation();
            }
        });

        meTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgList = new ArrayList<>();
                imgList.add(bean.getOriginalImg());
                ARouter.getInstance().build("/app/bigimg")
                        .withStringArrayList("imgUrl_list",imgList)
                        .withBoolean("is_click",false)
                        .navigation();
            }
        });

        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bean.getWidth(),
                    bean.getHeight());//两个400分别为添加图片的大小
            meTv.setLayoutParams(params);
            ImgUtil.INSTANCE.loadRoundImg(itemView.getContext(),bean.getOriginalImg(),meTv,8f,-1);
        }else{
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bean.getWidth(),
                    bean.getHeight());
            otherTv.setLayoutParams(params);
            ImgUtil.INSTANCE.loadRoundImg(itemView.getContext(),bean.getOriginalImg(),otherTv,8f,-1);
        }
    }
}