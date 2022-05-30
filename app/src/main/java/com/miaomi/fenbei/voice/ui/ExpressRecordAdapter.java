package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.MicSeatView;
import com.miaomi.fenbei.base.bean.ExpressItemBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.widget.HeartMeView;
import com.miaomi.fenbei.voice.R;
import com.xiaweizi.marquee.MarqueeTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpressRecordAdapter extends RecyclerView.Adapter<ExpressRecordAdapter.ItemHodler> {
    private Context context;
    private List<ExpressItemBean> mList = new ArrayList<>();
    private OnExpressItemOprateListener onExpressItemOprateListener;

    public void setOnExpressItemOprateListener(OnExpressItemOprateListener onExpressItemOprateListener) {
        this.onExpressItemOprateListener = onExpressItemOprateListener;
    }

    public ExpressRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ExpressItemBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<ExpressItemBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExpressRecordAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_express_record, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpressRecordAdapter.ItemHodler holder, final int position) {
        ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getSend_face(),holder.sendIv);
        ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getGet_face(),holder.reciveIv);
        ImgUtil.INSTANCE.loadGiftImg(context,mList.get(position).getGift_icon(),holder.giftIv);
        holder.sendNickName.setText(mList.get(position).getSend_nickname());
        holder.recNickName.setText(mList.get(position).getGet_nickname());
        holder.giftName.setText(mList.get(position).getGift_name());
        holder.timeTv.setText(TimeUtil.longFormatTime(mList.get(position).getTime()));
        holder.contentTv.setText(""+mList.get(position).getNote());
        holder.sendSeatMicView.loadSeat(mList.get(position).getSend_seat_frame());
        holder.getSendSeatMicView.loadSeat(mList.get(position).getGet_seat_frame());
        if(mList.get(position).getNote().length() >= 15){
            holder.contentTv.startScroll();
        }else{
            holder.contentTv.stopScroll();
        }
        if (mList.get(position).getHeart_num() > 0){
            holder.heartMeView.setVisibility(View.VISIBLE);
            holder.heartMeTv.setVisibility(View.VISIBLE);
            holder.heartMeView.setContent(mList.get(position).getHearts());
            holder.heartMeTv.setText("等"+mList.get(position).getHeart_num()+"人送上祝福");
        }else{
            holder.heartMeView.setVisibility(View.INVISIBLE);
            holder.heartMeTv.setVisibility(View.INVISIBLE);
        }
        if (mList.get(position).isHeart_status()){
            holder.expressTv.setText("已祝福");
        }else{
            holder.expressTv.setText("祝福");
        }

        holder.sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",mList.get(position).getSend_uid()+"")
                        .withString("user_name",mList.get(position).getSend_nickname())
                        .withString("user_face",mList.get(position).getSend_face())
                        .navigation();
            }
        });

        holder.reciveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",mList.get(position).getGet_uid()+"")
                        .withString("user_name",mList.get(position).getGet_nickname())
                        .withString("user_face",mList.get(position).getGet_face())
                        .navigation();
            }
        });
        holder.expressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExpressItemOprateListener != null){
                    if (!mList.get(position).isHeart_status()){
                        holder.expressTv.setText("已祝福");
                        mList.get(position).setHeart_status(true);
                        onExpressItemOprateListener.onHeart(mList.get(position),holder.heartMeView,holder.heartMeTv);
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ItemHodler extends RecyclerView.ViewHolder{
        private MarqueeTextView contentTv;
        private TextView timeTv;
        private ImageView giftIv;
        private ImageView sendIv;
        private ImageView reciveIv;
        private TextView expressTv;
        private HeartMeView heartMeView;
        private TextView heartMeTv;
        private TextView sendNickName;
        private TextView recNickName;
        private TextView giftName;
        private MicSeatView sendSeatMicView;
        private MicSeatView getSendSeatMicView;
        public ItemHodler(View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_express_content);
            giftIv = itemView.findViewById(R.id.iv_gift);
            sendIv = itemView.findViewById(R.id.iv_send);
            reciveIv = itemView.findViewById(R.id.iv_recive);
            expressTv = itemView.findViewById(R.id.tv_express);
            heartMeView = itemView.findViewById(R.id.iv_heart_me);
            heartMeTv = itemView.findViewById(R.id.tv_heart_num);
            giftName = itemView.findViewById(R.id.tv_gift_name);
            sendNickName = itemView.findViewById(R.id.tv_send);
            recNickName = itemView.findViewById(R.id.tv_recive);
            timeTv = itemView.findViewById(R.id.tv_time);
            sendSeatMicView = itemView.findViewById(R.id.iv_send_seat);
            getSendSeatMicView = itemView.findViewById(R.id.iv_get_seat);
        }
    }

    public interface OnExpressItemOprateListener{
        void onHeart(ExpressItemBean item, HeartMeView heartMeView, TextView heartNum);
    }
}
