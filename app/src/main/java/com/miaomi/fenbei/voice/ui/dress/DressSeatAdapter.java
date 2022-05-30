package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.MicSeatView;
import com.miaomi.fenbei.base.bean.DressItemBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DressSeatAdapter extends RecyclerView.Adapter<DressSeatAdapter.ViewHolder> {

    private Context context;
    private List<DressItemBean> list = new ArrayList<>();
    private OnDressItemClickListener onDressItemClickListener;

    public void setOnDressItemClickListener(OnDressItemClickListener onDressItemClickListener) {
        this.onDressItemClickListener = onDressItemClickListener;
    }

    public DressSeatAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DressItemBean> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DressSeatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item_dress_seat, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            holder.rootLl.setSelected(list.get(position).isSelected());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DressSeatAdapter.ViewHolder holder, final int position) {
        holder.nameTv.setText(list.get(position).getName());
        if (!TextUtils.isEmpty(list.get(position).getImg())) {
            holder.iconIv.loadSeat(list.get(position).getImg(),list.get(position).getReplaceArr());
        } else {
            holder.iconIv.loadDrawble(R.drawable.icon_dress_no_use);
        }
        if (!TextUtils.isEmpty(list.get(position).getTime())) {
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(list.get(position).getTime() + "天");
        } else {
            holder.timeTv.setVisibility(View.INVISIBLE);
        }
        if (list.get(position).getPrice() > 0) {
            holder.priceTv.setVisibility(View.VISIBLE);
            holder.priceTv.setText(list.get(position).getPrice() + "钻石");
        } else {
            holder.priceTv.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            onDressItemClickListener.onClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MicSeatView iconIv;
        TextView nameTv;
        LinearLayout rootLl;
        ImageView faceIv;
        private TextView timeTv;
        private TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            rootLl = itemView.findViewById(R.id.ll_root);
            priceTv = itemView.findViewById(R.id.tv_price);
            timeTv = itemView.findViewById(R.id.tv_time);
            faceIv = itemView.findViewById(R.id.iv_face);
        }
    }
}
