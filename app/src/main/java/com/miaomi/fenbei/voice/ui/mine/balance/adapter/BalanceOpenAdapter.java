package com.miaomi.fenbei.voice.ui.mine.balance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miaomi.fenbei.base.bean.IncomeHistoryBean;
import com.miaomi.fenbei.voice.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class BalanceOpenAdapter extends RecyclerView.Adapter<BalanceOpenAdapter.ItemHolder> {

    private List<IncomeHistoryBean.ListBeanX.ListBean> mData = new ArrayList<>();
    public void setData(List<IncomeHistoryBean.ListBeanX.ListBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_balance_open, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tvTime.setText(mData.get(position).getTime());
        holder.tvProfit.setText(mData.get(position).getCash()+"元");
        holder.tvMoney.setText("+" + mData.get(position).getMoney());
        holder.tvPercent.setText(mData.get(position).getPercent());
        if (mData.get(position).getType() == 0){
            holder.typeTv.setText("个人");
        }else if (mData.get(position).getType() == 1){
            holder.typeTv.setText("房间");
        }else if (mData.get(position).getType() == 2){
            holder.typeTv.setText("家族");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;
        private TextView tvProfit;
        private TextView tvPercent;
        private TextView tvMoney;
        private TextView typeTv;
        ItemHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvProfit = itemView.findViewById(R.id.tv_profit);
            tvPercent = itemView.findViewById(R.id.tv_percent);
            tvMoney = itemView.findViewById(R.id.tv_money);
            typeTv = itemView.findViewById(R.id.tv_type);
        }
    }
}
