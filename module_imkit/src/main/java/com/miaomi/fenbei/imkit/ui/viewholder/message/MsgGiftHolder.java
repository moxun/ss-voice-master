package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.imkit.R;

public class MsgGiftHolder extends MsgBaseHolder{
    private TextView otherContentTv;
    private TextView meContentTv;
    private TextView otherPriceTv;
    private TextView mePriceTv;
    private ImageView meIv;
    private ImageView othetIv;
    public MsgGiftHolder(View itemView) {
        super(itemView);
        otherContentTv = itemView.findViewById(R.id.tv_other_content);
        meContentTv = itemView.findViewById(R.id.tv_me_content);
        otherPriceTv = itemView.findViewById(R.id.tv_other_price);
        mePriceTv = itemView.findViewById(R.id.tv_me_price);
        meIv = itemView.findViewById(R.id.iv_me_gift);
        othetIv = itemView.findViewById(R.id.iv_other_gift);
    }

    @Override
    void bindChildData(final C2CMsgBean bean) {
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            meContentTv.setText(bean.getCustomBean().getContent());
            mePriceTv.setText(bean.getCustomBean().getPrice());
            ImgUtil.INSTANCE.loadImg(itemView.getContext(),bean.getCustomBean().getGiftUrl(),meIv);
        }else{
            otherContentTv.setText(bean.getCustomBean().getContent());
            otherPriceTv.setText(bean.getCustomBean().getPrice());
            ImgUtil.INSTANCE.loadImg(itemView.getContext(),bean.getCustomBean().getGiftUrl(),othetIv);
        }
    }

}