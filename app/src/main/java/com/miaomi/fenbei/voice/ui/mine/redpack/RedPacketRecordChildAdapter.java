package com.miaomi.fenbei.voice.ui.mine.redpack;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.RedPacketRecordBean;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RedPacketRecordChildAdapter  extends RecyclerView.Adapter<RedPacketRecordChildAdapter.ItemHodler> {
    private Context context;
    private List<RedPacketRecordBean.ListBean> mList = new ArrayList<>();

    public RedPacketRecordChildAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RedPacketRecordBean.ListBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<RedPacketRecordBean.ListBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.room_item_red_pack_record_child, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHodler holder, final int position) {
        holder.descTv.setText(mList.get(position).getDesc());
        holder.priceTv.setText(mList.get(position).getDiamonds()+"钻石");
        if (mList.get(position).getCreate_time() > 0){
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(TimeUtil.getDayTime(mList.get(position).getCreate_time()));
        }else{
            holder.timeTv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mList.get(position).getOrder_no())){
                GetRedPacketUsersDialog dialog =new GetRedPacketUsersDialog(context,mList.get(position).getOrder_no());
                dialog.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ItemHodler extends RecyclerView.ViewHolder{
        private TextView descTv;
        private TextView priceTv;
        private TextView timeTv;
        public ItemHodler(View itemView) {
            super(itemView);
            descTv = itemView.findViewById(R.id.tv_desc);
            priceTv = itemView.findViewById(R.id.tv_price);
            timeTv = itemView.findViewById(R.id.tv_time);
        }
    }
}

