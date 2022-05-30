package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.HdCenterItemBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class HdCenterListAdapter extends RecyclerView.Adapter<HdCenterListAdapter.ItemHodler> {
    private Context context;
    private List<HdCenterItemBean> mList = new ArrayList<>();

    public HdCenterListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HdCenterItemBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<HdCenterItemBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HdCenterListAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HdCenterListAdapter.ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_hd_center, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HdCenterListAdapter.ItemHodler holder, final int position) {
        ImgUtil.INSTANCE.loadRoundImg(holder.itemView.getContext(),mList.get(position).getCover(),holder.contentIv,8f,R.drawable.common_banner_plachodler);
        holder.titleTv.setText(mList.get(position).getTitle());
        holder.desTv.setText(mList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(holder.itemView.getContext(),mList.get(position).getUrl(),mList.get(position).getTitle());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private ImageView contentIv;
        private TextView desTv;
//        private TextView heartMeTv;

        public ItemHodler(View itemView) {
            super(itemView);
            contentIv = itemView.findViewById(R.id.iv_content);
            desTv = itemView.findViewById(R.id.tv_des);
//            heartMeTv = itemView.findViewById(R.id.tv_heart_num);
            titleTv = itemView.findViewById(R.id.tv_title);
        }
    }

}
