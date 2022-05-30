package com.miaomi.fenbei.gift.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftNumSelectBean;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.gift.listener.OnGifiNumSeletedListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GiftNumAdapter extends RecyclerView.Adapter<GiftNumAdapter.ItemHolder> {


    private Context mContext;
    private List<GiftNumSelectBean> datas = new ArrayList<>();
    private OnGifiNumSeletedListener spinerItemClickListener;

    public GiftNumAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<GiftNumSelectBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setOnSpinerItemClickListener(OnGifiNumSeletedListener spinerItemClickListener) {
        this.spinerItemClickListener = spinerItemClickListener;
    }

    @Override
    public GiftNumAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiftNumAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_number, parent, false));
    }


    @Override
    public void onBindViewHolder(final GiftNumAdapter.ItemHolder holder, final int position) {
        holder.numberTv.setText(datas.get(position).getNum());
//        holder.desTv.setText(datas.get(position).getDes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinerItemClickListener != null) {
                    spinerItemClickListener.onItemClick(datas.get(position).getNum());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView numberTv;

        ItemHolder(View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.tv_num);
        }
    }
}

