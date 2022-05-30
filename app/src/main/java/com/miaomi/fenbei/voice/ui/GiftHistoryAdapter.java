package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.GiftHistoryBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GiftHistoryAdapter extends RecyclerView.Adapter<GiftHistoryAdapter.ItemHodler> {
    private Context context;
    private List<GiftHistoryBean.ListBean> mList = new ArrayList<>();
    private int giftType = GiftHistoryFragment.TYPE_GIFT_HISTORY_SEND;

    public GiftHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GiftHistoryBean.ListBean> list,int giftType){
        this.giftType = giftType;
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<GiftHistoryBean.ListBean> list,int giftType){
        this.giftType = giftType;
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_gift_history,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHodler holder, final int position) {
        if (mList.get(position).getD_fan_type() == 0){
            holder.numberTv.setVisibility(View.VISIBLE);
            holder.giftIconIv.setVisibility(View.VISIBLE);
            if (giftType == GiftHistoryFragment.TYPE_GIFT_HISTORY_SEND){
                holder.statusTv.setText("送出");
            }else{
                holder.statusTv.setText("收到");
            }
        }else{
            if (giftType == GiftHistoryFragment.TYPE_GIFT_HISTORY_SEND){
                holder.statusTv.setText("加入");
            }else{
                holder.statusTv.setText("新增");
            }
            holder.giftIconIv.setVisibility(View.GONE);
            holder.numberTv.setVisibility(View.GONE);
        }
        holder.timeTv.setText(mList.get(position).getCreate_time());
        holder.priceTv.setText(mList.get(position).getGift_name()+"（"+ mList.get(position).getPrice() +"钻石）");
        holder.numberTv.setText("x" + mList.get(position).getNum());
        holder.nameTv.setText(mList.get(position).getNickname());

        if(!TextUtils.isEmpty(mList.get(position).getIcon())){
            ImgUtil.INSTANCE.loadGiftImg(context,mList.get(position).getIcon(),holder.giftIconIv);
        }
        if(!TextUtils.isEmpty(mList.get(position).getFace())){
            ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getFace(),holder.avterIv);
        }

        holder.avterIv.setOnClickListener(v -> ARouter.getInstance().build("/app/userhomepage")
                .withString("user_id",mList.get(position).getUser_id()+"")
                .withString("user_name",mList.get(position).getNickname())
                .withString("user_face",mList.get(position).getFace())
                .navigation());
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ItemHodler extends RecyclerView.ViewHolder{
        ImageView avterIv;
        TextView nameTv;
        DCBTextView numberTv;
        ImageView giftIconIv;
        DCBTextView priceTv;
        TextView timeTv;
        TextView statusTv;
        public ItemHodler(View itemView) {
            super(itemView);
            avterIv = itemView.findViewById(R.id.iv_avter);
            nameTv = itemView.findViewById(R.id.tv_name);
            numberTv = itemView.findViewById(R.id.tv_number);
            giftIconIv = itemView.findViewById(R.id.iv_gift_icon);
            priceTv = itemView.findViewById(R.id.tv_price);
            timeTv = itemView.findViewById(R.id.tv_time);
            statusTv = itemView.findViewById(R.id.tv_status);
        }
    }
}
