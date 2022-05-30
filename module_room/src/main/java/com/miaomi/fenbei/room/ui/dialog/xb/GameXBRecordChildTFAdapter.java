package com.miaomi.fenbei.room.ui.dialog.xb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.XBTFRecordBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.room.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameXBRecordChildTFAdapter extends RecyclerView.Adapter<GameXBRecordChildTFAdapter.ViewHolder> {

    private List<XBTFRecordBean> mList = new ArrayList<>();
    private Context context;

    public GameXBRecordChildTFAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<XBTFRecordBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<XBTFRecordBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameXBRecordChildTFAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameXBRecordChildTFAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_xb_record_tf,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final GameXBRecordChildTFAdapter.ViewHolder holder, final int position) {
        ImgUtil.INSTANCE.loadGiftImg(context,mList.get(position).getGift_icon(),holder.giftIv);
        holder.timeTv.setText(TimeUtil.getDataAndFmTime(mList.get(position).getCreate_time()));
        holder.priceTv.setText("("+mList.get(position).getGift_price()+"钻石)");
        if (mList.get(position).getKind() == 0){
            holder.statusTv.setText("投放中");
        }else if (mList.get(position).getKind() == 1){
            holder.statusTv.setText("已被挖掘");
        }else{
            holder.statusTv.setText("退回");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView priceTv;
        private TextView statusTv;
        private TextView numTv;
        private ImageView giftIv;
        private TextView timeTv;
        public ViewHolder(View itemView) {
            super(itemView);
            statusTv = itemView.findViewById(R.id.tv_status);
            priceTv = itemView.findViewById(R.id.tv_price);
            numTv = itemView.findViewById(R.id.tv_num);
            giftIv = itemView.findViewById(R.id.iv_gift);
            timeTv = itemView.findViewById(R.id.tv_time);
        }
    }
}
