package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.view.View;
import android.widget.ImageView;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.imkit.R;


public class MsgEmojiHolder extends MsgBaseHolder{
    private ImageView otherTv;
    private ImageView meTv;
    public MsgEmojiHolder(View itemView) {
        super(itemView);
        otherTv = itemView.findViewById(R.id.tv_other);
        meTv = itemView.findViewById(R.id.tv_me);
    }

    @Override
    void bindChildData(final C2CMsgBean bean) {
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
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            ImgUtil.INSTANCE.loadImg(itemView.getContext(),bean.getEmojiUrl(),meTv);
        }else{
            ImgUtil.INSTANCE.loadImg(itemView.getContext(),bean.getEmojiUrl(),otherTv);
        }
    }

}
