package com.miaomi.fenbei.room.ui.dialog;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.DtFansBean;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.OnRadioFansSeletedListener;

import java.util.List;

public class SpinerAdapter extends RecyclerView.Adapter<SpinerAdapter.ItemHolder> {


    private Context mContext;
    private List<DtFansBean.ConfigBean> datas;
    private OnRadioFansSeletedListener spinerItemClickListener;

    public SpinerAdapter(Context mContext, List<DtFansBean.ConfigBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public void setOnSpinerItemClickListener(OnRadioFansSeletedListener spinerItemClickListener) {
        this.spinerItemClickListener = spinerItemClickListener;
    }

    @Override
    public SpinerAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpinerAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_spiner_num, parent, false));
    }


    @Override
    public void onBindViewHolder(final SpinerAdapter.ItemHolder holder, final int position) {
        holder.contentTv.setText(datas.get(position).getDay()+"天");
        holder.desTv.setText(""+datas.get(position).getZuan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinerItemClickListener != null) {
                    spinerItemClickListener.onItemClick(datas.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        TextView desTv;

        ItemHolder(View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_content);
            desTv = itemView.findViewById(R.id.tv_des);
        }
    }

}
