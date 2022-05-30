package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.DressItemBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DressZJAdapter  extends RecyclerView.Adapter<DressZJAdapter.ViewHolder> {

    private Context context;
    private List<DressItemBean> list = new ArrayList<>();
    private OnDressItemClickListener onDressItemClickListener;

    public void setOnDressItemClickListener(OnDressItemClickListener onDressItemClickListener) {
        this.onDressItemClickListener = onDressItemClickListener;
    }

    public DressZJAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DressItemBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DressZJAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item_dress_zj,parent,false);
        return new DressZJAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DressZJAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
        }else{
            holder.rootLl.setSelected(list.get(position).isSelected());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DressZJAdapter.ViewHolder holder, final int position) {

//        showSvgaGiftAnim(context,holder.zjIv,list.get(position).getImg());
        ImgUtil.INSTANCE.loadImg(context,list.get(position).getSeat_img(),holder.zjStaticIv);
        holder.nameTv.setText(list.get(position).getName());
        if (!TextUtils.isEmpty(list.get(position).getTime())){
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(list.get(position).getTime()+"天");
        }else{
            holder.timeTv.setVisibility(View.INVISIBLE);
        }
        if (list.get(position).getPrice() > 0){
            holder.priceTv.setVisibility(View.VISIBLE);
            holder.priceTv.setText(list.get(position).getPrice()+"钻石");
        }else{
            holder.priceTv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            onDressItemClickListener.onClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        LinearLayout rootLl;
        ImageView zjStaticIv;
        private TextView timeTv;
        private TextView priceTv;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            rootLl = itemView.findViewById(R.id.ll_root);
            priceTv = itemView.findViewById(R.id.tv_price);
            timeTv = itemView.findViewById(R.id.tv_time);
            zjStaticIv = itemView.findViewById(R.id.iv_svga_static);
        }
    }

}
