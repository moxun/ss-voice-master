package com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftWallBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AllGiftWallAdapter extends RecyclerView.Adapter<AllGiftWallAdapter.ItemHolder> {

    private List<GiftWallBean.ListBean> mData = new ArrayList<>();
    private Context mContext;

    public AllGiftWallAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<GiftWallBean.ListBean> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<GiftWallBean.ListBean> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_gift_wall, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        GiftWallBean.ListBean  bean = mData.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvNumber.setText("x "+bean.getNumber());

//        holder.ratingBar.setStepSize(2.0f);
        if(bean.getNumber()<=0){
            holder.uncheckedTv.setVisibility(View.VISIBLE);
            holder.ratingBar.setVisibility(View.GONE);
            ImgUtil.INSTANCE.loadGrayImg(mContext,bean.getIcon(),holder.ivGiftIcon);
        }else{
            holder.uncheckedTv.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(bean.getStart_num());
            ImgUtil.INSTANCE.loadImg(mContext,bean.getIcon(),holder.ivGiftIcon);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView ivGiftIcon;
        private TextView tvNumber;
        private TextView tvName;
        private RatingBar ratingBar;
        private  TextView uncheckedTv;
        ItemHolder(View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            ivGiftIcon = itemView.findViewById(R.id.gift_iv);
            tvNumber =  itemView.findViewById(R.id.num_tv);
            tvName =  itemView.findViewById(R.id.gift_name_tv);
            uncheckedTv=itemView.findViewById(R.id.tv_unchecked);
        }
    }
}