package com.miaomi.fenbei.voice.ui.mine.balance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.IncomeHistoryBean;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.ItemHolder> {

    private List<IncomeHistoryBean.ListBeanX> mData = new ArrayList<>();
    private Context mContext;

    public BalanceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<IncomeHistoryBean.ListBeanX> data ) {
        this.mData = data;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_balance, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        holder.tvTime.setText(mData.get(position).getMonth());
        holder.tvTotal.setText(mData.get(position).getCash() + "元");
        holder.ivOpen.setActivated(true);
        holder.rvProfitInfo.setVisibility(View.GONE);
        if (!holder.mBalanceOpenAdapter.hasObservers()){
            holder.rvProfitInfo.setAdapter(holder.mBalanceOpenAdapter);
        }else {
            holder.mBalanceOpenAdapter.notifyDataSetChanged();
        }
        holder.rvProfitInfo.setPullRefreshEnabled(false);
        holder.rvProfitInfo.setLoadingMoreEnabled(false);

        View headView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.user_head_item_balance_open,null);
        headView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.rvProfitInfo.addHeaderView(headView);
        holder.itemView.setOnClickListener(v -> {
            if (holder.ivOpen.isActivated()){
                holder.ivOpen.setActivated(false);
                holder.ivOpen.setBackgroundResource(R.drawable.user_profit_open);
                holder.rvProfitInfo.setVisibility(View.VISIBLE);
                holder.rvProfitInfo.setLayoutManager(new LinearLayoutManager(mContext));
                if (mData.get(position).getList() == null || mData.get(position).getList().size() == 0){
                    return;
                }else {

                    holder.mBalanceOpenAdapter.setData(mData.get(position).getList());
                }
            }else {
                holder.ivOpen.setActivated(true);
                holder.ivOpen.setBackgroundResource(R.drawable.user_profit_retract);
                holder.rvProfitInfo.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvTotal;
        private ImageView ivOpen;
        private XRecyclerView rvProfitInfo;
        private BalanceOpenAdapter mBalanceOpenAdapter;

        public ItemHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTotal = itemView.findViewById(R.id.tv_total);
            ivOpen = itemView.findViewById(R.id.iv_open);
            rvProfitInfo = itemView.findViewById(R.id.rv_profit_info);
            mBalanceOpenAdapter = new BalanceOpenAdapter();
        }
    }
}
