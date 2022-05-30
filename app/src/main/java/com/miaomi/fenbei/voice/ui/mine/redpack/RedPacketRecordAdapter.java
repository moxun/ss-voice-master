package com.miaomi.fenbei.voice.ui.mine.redpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.RedPacketRecordBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RedPacketRecordAdapter extends RecyclerView.Adapter<RedPacketRecordAdapter.ItemHodler> {
    private Context context;
    private List<RedPacketRecordBean> mList = new ArrayList<>();

    public RedPacketRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RedPacketRecordBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<RedPacketRecordBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.room_item_red_pack_record, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHodler holder, final int position) {
        holder.sumTv.setText(mList.get(position).getMonth()+"   红包总价值 "+mList.get(position).getMoney()+"钻石");
        RedPacketRecordChildAdapter adapter = new RedPacketRecordChildAdapter(context);
        holder.recordRv.setLayoutManager(new LinearLayoutManager(context));
        holder.recordRv.setAdapter(adapter);
        adapter.setData(mList.get(position).getList());
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ItemHodler extends RecyclerView.ViewHolder{
        private TextView sumTv;
        private RecyclerView recordRv;
        public ItemHodler(View itemView) {
            super(itemView);
            sumTv = itemView.findViewById(R.id.tv_sum);
            recordRv = itemView.findViewById(R.id.rv_record);
        }
    }
}

