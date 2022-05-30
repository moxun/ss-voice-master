package com.miaomi.fenbei.voice.ui.noble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.NobleBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NobleCenterAdapter  extends RecyclerView.Adapter<NobleCenterAdapter.ItemHodler> {
    private Context context;
    private List<NobleBean.PrivilegeListBean> list = new ArrayList<>();

    public NobleCenterAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NobleBean.PrivilegeListBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_noble_center, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NobleCenterAdapter.ItemHodler holder, final int position) {
        holder.nameTv.setText(list.get(position).getName());
        holder.desTv.setText(list.get(position).getDescription());
        ImgUtil.INSTANCE.loadImg(context,list.get(position).getIcon(),holder.iconIv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView desTv;
        ImageView iconIv;
        public ItemHodler(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            desTv = itemView.findViewById(R.id.tv_des);
            iconIv = itemView.findViewById(R.id.iv_icon);
        }
    }
}