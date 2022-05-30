package com.miaomi.fenbei.voice.ui.mine.balance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BalanceHistoryBean;
import com.miaomi.fenbei.voice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BalanceHistoryAdapter extends RecyclerView.Adapter<BalanceHistoryAdapter.ViewHolder> {

    private List<BalanceHistoryBean> mList;
    private Context context;

    public BalanceHistoryAdapter(List<BalanceHistoryBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public BalanceHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BalanceHistoryAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_balance_history, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final BalanceHistoryAdapter.ViewHolder holder, int position) {
        holder.createtimeTv.setText(mList.get(position).getTime());
        holder.priceTv.setText(mList.get(position).getDiamond() + mList.get(position).getSend() + "钻石");
        holder.linenoTv.setText(mList.get(position).getPrice() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView createtimeTv;
        TextView linenoTv;
        TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            createtimeTv = itemView.findViewById(R.id.tv_createtime);
            priceTv = itemView.findViewById(R.id.tv_price);
            linenoTv = itemView.findViewById(R.id.tv_lineno);
        }
    }
}
