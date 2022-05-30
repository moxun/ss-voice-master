package com.miaomi.fenbei.room.ui.dialog.xb;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.XBXBRecordBean;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameXBRecordChildXBAdapter extends RecyclerView.Adapter<GameXBRecordChildXBAdapter.ViewHolder> {

    private List<XBXBRecordBean> mList = new ArrayList<>();
    private Context context;

    public GameXBRecordChildXBAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<XBXBRecordBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<XBXBRecordBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameXBRecordChildXBAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameXBRecordChildXBAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_xb_record_xb,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final GameXBRecordChildXBAdapter.ViewHolder holder, final int position) {
//        ImgUtil.INSTANCE.loadGiftImg(context,mList.get(position).getGift_icon(),holder.giftIv);
        holder.timeTv.setText(TimeUtil.getDataAndFmTime(mList.get(position).getCreate_time()));
        holder.priceTv.setText(mList.get(position).getUse_price()+"钻石");
        if (mList.get(position).getType() == 1){
            holder.statusTv.setTextColor(Color.parseColor("#FF934E"));
            holder.statusTv.setText("获得价值："+mList.get(position).getLucky_price()+"钻石礼物");
        }else{
            holder.statusTv.setTextColor(Color.parseColor("#666666"));
            holder.statusTv.setText("未中奖");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView priceTv;
        private TextView statusTv;
        private TextView timeTv;
        public ViewHolder(View itemView) {
            super(itemView);
            statusTv = itemView.findViewById(R.id.tv_status);
            priceTv = itemView.findViewById(R.id.tv_price);
            timeTv = itemView.findViewById(R.id.tv_time);
        }
    }
}
