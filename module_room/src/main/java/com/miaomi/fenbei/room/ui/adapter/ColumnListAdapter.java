package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;


public class ColumnListAdapter extends RecyclerView.Adapter<ColumnListAdapter.ViewHolder> {

    private List<RadioStationBean> mList = new ArrayList<>();
    private Context context;

    public ColumnListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RadioStationBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColumnListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColumnListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_coulmn_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ColumnListAdapter.ViewHolder holder, final int position) {
        holder.playIv.setVisibility(View.GONE);
        holder.dot_playTv.setVisibility(View.GONE);
        holder.dot_not_playTv.setVisibility(View.VISIBLE);
        holder.coulmn_titleTv.setTextColor(Color.parseColor("#66ffffff"));
        if (mList.get(position).getStatus() == 0) {
            holder.play_stateTv.setText("已结束");

            holder.play_stateTv.setTextColor(Color.parseColor("#33ffffff"));
        } else if (mList.get(position).getStatus() == 1) {
            holder.dot_playTv.setVisibility(View.VISIBLE);
            holder.dot_not_playTv.setVisibility(View.GONE);
            holder.play_stateTv.setText("直播中");
            holder.coulmn_titleTv.setTextColor(Color.parseColor("#ffffff"));
            holder.play_stateTv.setTextColor(Color.parseColor("#ED52F9"));
            holder.playIv.setVisibility(View.VISIBLE);
        } else if(mList.get(position).getStatus() == 2) {
            holder.play_stateTv.setText("未开始");
            holder.play_stateTv.setTextColor(Color.parseColor("#66FFFFFF"));
        }
        holder.coulmn_topicTv.setText("今日话题:" + mList.get(position).getToday_topic());
        holder.coulmn_timeTv.setText(mList.get(position).getTime_period_start() + "-" + mList.get(position).getTime_period_end());
        if (!TextUtils.isEmpty(mList.get(position).getIcon())) {
            ImgUtil.INSTANCE.loadHomeHotImg(context, mList.get(position).getIcon(), holder.column_iconIv, 6f, -1);
        }
        holder.nameTv.setText(mList.get(position).getNickname());
        holder.coulmn_titleTv.setText(mList.get(position).getRadio_name());
        if (!TextUtils.isEmpty(mList.get(position).getFace())) {
            ImgUtil.INSTANCE.loadFaceIcon(context, mList.get(position).getFace(), holder.avterIv);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != checkItemListener) {
                    checkItemListener.itemChecked(mList.get(position));
                }
            }
        });
    }

    CheckItemListener checkItemListener;

    public void setOnCheckItemListener(CheckItemListener checkItemListener) {
        this.checkItemListener = checkItemListener;
    }

    public interface CheckItemListener {
        void itemChecked(RadioStationBean radioStationBean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView play_stateTv;
        private TextView coulmn_timeTv;
        private ImageView playIv;
        private TextView dot_not_playTv;
        private TextView dot_playTv;
        private TextView coulmn_titleTv;
        private TextView coulmn_topicTv;
        private ImageView column_iconIv;
        private ImageView avterIv;
        private TextView nameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            coulmn_timeTv = itemView.findViewById(R.id.tv_coulmn_time);
            play_stateTv = itemView.findViewById(R.id.tv_play_state);
            playIv = itemView.findViewById(R.id.iv_play);
            dot_not_playTv = itemView.findViewById(R.id.tv_dot_not_play);
            dot_playTv = itemView.findViewById(R.id.tv_dot_play);
            coulmn_titleTv = itemView.findViewById(R.id.tv_coulmn_title);
            coulmn_topicTv = itemView.findViewById(R.id.tv_coulmn_topic);
            column_iconIv = itemView.findViewById(R.id.iv_column_icon);
            avterIv = itemView.findViewById(R.id.iv_avter);
            nameTv = itemView.findViewById(R.id.tv_name);
        }
    }
}