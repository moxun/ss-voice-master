package com.miaomi.fenbei.voice.ui.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.PayHistroyBean;
import com.miaomi.fenbei.voice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PayHistroyAdapter extends RecyclerView.Adapter<PayHistroyAdapter.ViewHolder> {

    private List<PayHistroyBean> mList;
    private Context context;

    public PayHistroyAdapter(List<PayHistroyBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public PayHistroyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayHistroyAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.pay_item_history,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull final PayHistroyAdapter.ViewHolder holder, int position) {
        holder.createtimeTv.setText(mList.get(position).getCreate_time());
        //数量增加
        if (mList.get(position).getType() == 1){
            holder.priceTv.setText("+"+mList.get(position).getDiamonds());
            holder.priceTv.setSelected(true);
        }else {
            holder.priceTv.setSelected(false);
            holder.priceTv.setText("-"+mList.get(position).getDiamonds());
        }
        holder.contentTv.setText(mList.get(position).getDesc());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.itemView.setSelected(true);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView createtimeTv;
        TextView contentTv;
        TextView priceTv;
        public ViewHolder(View itemView) {
            super(itemView);
            createtimeTv = itemView.findViewById(R.id.tv_time);
            priceTv = itemView.findViewById(R.id.tv_price);
            contentTv = itemView.findViewById(R.id.tv_content);
        }
    }
}
