package com.miaomi.fenbei.voice.ui.mine.cash_withdrawal.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.CashOutRecordBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ItemHolder>{

    private List<CashOutRecordBean.ListBean> mData = new ArrayList<>();
    public void setData(List<CashOutRecordBean.ListBean> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(List<CashOutRecordBean.ListBean> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_record, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tvMoney.setText(mData.get(position).getMoney() + "元");
        holder.tvOrderNumber.setText(mData.get(position).getId());
        holder.tvTime.setText(mData.get(position).getCashout_time());
        switch (mData.get(position).getStatus()){
            case 0:
                holder.tvState.setText("待审核");
                holder.tvState.setTextColor(Color.parseColor("#FD7F8F"));
                break;
            case 1:
                holder.tvState.setText("正在打款");
                holder.tvState.setTextColor(Color.parseColor("#FD7F8F"));
                break;
            case 2:
                holder.tvState.setText("已驳回");
                holder.tvState.setTextColor(Color.parseColor("#666666"));
                break;
            case 3:
                holder.tvState.setText("提现成功");
                holder.tvState.setTextColor(Color.parseColor("#666666"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView tvMoney;
        private TextView tvOrderNumber;
        private TextView tvState;

        public ItemHolder(View itemView) {
            super(itemView);
            tvTime = (TextView)itemView.findViewById(R.id.tv_time);
            tvMoney = (TextView)itemView.findViewById(R.id.tv_money);
            tvOrderNumber = (TextView)itemView.findViewById(R.id.tv_order_number);
            tvState = (TextView)itemView.findViewById(R.id.tv_state);
        }
    }
}
