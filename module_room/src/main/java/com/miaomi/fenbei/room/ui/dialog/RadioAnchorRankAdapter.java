package com.miaomi.fenbei.room.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.AnchorFansItemBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

public class RadioAnchorRankAdapter extends RecyclerView.Adapter<RadioAnchorRankAdapter.ItemHolder> {

    private List<AnchorFansItemBean> mList = new ArrayList<>();
    private Context mContext;


    public RadioAnchorRankAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AnchorFansItemBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RadioAnchorRankAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RadioAnchorRankAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_radio_anchor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RadioAnchorRankAdapter.ItemHolder holder, final int position) {

        holder.numberTv.setVisibility(View.VISIBLE);
        holder.numberTv.setText(String.valueOf(position + 1));
        holder.positionIv.setVisibility(View.GONE);
        if (mList.get(position).getDay() != 0) {
            holder.fansDayTv.setText("粉丝天数：" + mList.get(position).getDay()+"天");
        } else {
            holder.fansDayTv.setText("粉丝量：" + mList.get(position).getFans_num());
        }
        if (position == 0) {
            holder.numberTv.setVisibility(View.GONE);
            holder.positionIv.setVisibility(View.VISIBLE);
            holder.positionIv.setImageResource(R.drawable.item_radio_anchor_num_1);
        }
        if (position == 1) {
            holder.numberTv.setVisibility(View.GONE);
            holder.positionIv.setVisibility(View.VISIBLE);
            holder.positionIv.setImageResource(R.drawable.item_radio_anchor_num_2);
        }
        if (position == 2) {
            holder.numberTv.setVisibility(View.GONE);
            holder.positionIv.setVisibility(View.VISIBLE);
            holder.positionIv.setImageResource(R.drawable.item_radio_anchor_num_3);
        }
        if(mList.get(position).getType()==0){
            holder.fansIv.setVisibility(View.GONE);
        }
        if (mList.get(position).getType() == 4){
            holder.fansIv.setImageLevel(2);
        }
        holder.fansIv.setVisibility(View.VISIBLE);
        if (mList.get(position).getType() == 3){
            holder.fansIv.setImageLevel(3);
        }
        if (mList.get(position).getType() == 2){
            holder.fansIv.setImageLevel(4);
        }
        if (mList.get(position).getType() == 1) {
            holder.fansIv.setImageLevel(5);
        }

//        ImgUtil.INSTANCE.loadImg(mContext, mList.get(position).ge(), holder.fansIv);

        if (mList.get(position).getFace() != null){
            ImgUtil.INSTANCE.loadFaceIcon(mContext, mList.get(position).getFace(), holder.iconIv);
        }
        holder.nameTv.setText(mList.get(position).getNickname());
        if (TextUtils.isEmpty(mList.get(position).getFans_end_time())) {
            holder.fansDayTv.setVisibility(View.VISIBLE);
            holder.totalTv.setText("" + mList.get(position).getHot_value());
        } else {
            holder.fansDayTv.setVisibility(View.GONE);
            holder.totalTv.setText(mList.get(position).getFans_end_time());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id", mList.get(position).getUser_id() + "")
                        .withString("user_name", mList.get(position).getNickname())
                        .withString("user_face", mList.get(position).getFace())
                        .navigation();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView numberTv;
        ImageView iconIv;
        TextView nameTv;
        LevelView gongxianIv;
        TextView totalTv;
        ImageView positionIv;
        TextView fansDayTv;
        ImageView fansIv;

        public ItemHolder(View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.tv_number);
            iconIv = itemView.findViewById(R.id.tv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            totalTv = itemView.findViewById(R.id.tv_total);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            positionIv = itemView.findViewById(R.id.tv_position);
            fansDayTv = itemView.findViewById(R.id.tv_fans_day);
            fansIv = itemView.findViewById(R.id.iv_fans);
        }
    }
}
