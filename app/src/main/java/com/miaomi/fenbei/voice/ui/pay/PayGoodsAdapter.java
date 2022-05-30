package com.miaomi.fenbei.voice.ui.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GoodsBean;
import com.miaomi.fenbei.voice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PayGoodsAdapter extends RecyclerView.Adapter<PayGoodsAdapter.ViewHolder> {

    private List<GoodsBean> mList;
    private Context context;
    private OnSelectedGoodsListener onSelectedGoodsListener;

    public PayGoodsAdapter(List<GoodsBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setOnSelectedGoodsListener(OnSelectedGoodsListener onSelectedGoodsListener) {
        this.onSelectedGoodsListener = onSelectedGoodsListener;
    }

    public interface OnSelectedGoodsListener{
        void onSelectedGoods(GoodsBean bean);
    }

    @NonNull
    @Override
    public PayGoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayGoodsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.pay_item_goods,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final PayGoodsAdapter.ViewHolder holder, final int position) {
        holder.priceTv.setText(String.format("¥ %s",mList.get(position).getPay_money()));
        holder.priceMoneyTv.setText(mList.get(position).getTrade_name());
        holder.itemView.setSelected(mList.get(position).isSelected());
//        if (mList.get(position).getSend() == 0){
//            holder.giveTv.setVisibility(View.GONE);
//        }else{
//            holder.giveTv.setVisibility(View.VISIBLE);
//            holder.giveTv.setText("加送"+mList.get(position).getSend());
//        }
        holder.itemView.setOnClickListener(v -> {
            for (GoodsBean bean : mList){
                bean.setSelected(false);
            }
            mList.get(position).setSelected(true);
            if (onSelectedGoodsListener != null){
                onSelectedGoodsListener.onSelectedGoods(mList.get(position));
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView priceMoneyTv;
        TextView priceTv;
//        TextView giveTv;
        public ViewHolder(View itemView) {
            super(itemView);
            priceMoneyTv = itemView.findViewById(R.id.tv_price_money);
            priceTv = itemView.findViewById(R.id.tv_price);
//            giveTv = itemView.findViewById(R.id.tv_give);
        }
    }
}
