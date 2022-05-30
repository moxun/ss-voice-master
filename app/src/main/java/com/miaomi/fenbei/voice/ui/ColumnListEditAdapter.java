package com.miaomi.fenbei.voice.ui;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.bean.RoomGameListBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;


import java.util.ArrayList;
import java.util.List;


public class ColumnListEditAdapter extends RecyclerView.Adapter<ColumnListEditAdapter.ViewHolder> {

    private List<RadioStationBean> mList=new ArrayList<>();
    private Context context;

    public ColumnListEditAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<RadioStationBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColumnListEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColumnListEditAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.column_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ColumnListEditAdapter.ViewHolder holder, final int position) {
        holder.coulmn_titleTv.setText(mList.get(position).getRadio_name());
        holder.coulmn_timeTv.setText("本档时间:"+mList.get(position).getTime_period_start()+"-"+mList.get(position).getTime_period_end());
        if(!TextUtils.isEmpty(mList.get(position).getIcon())) {
            ImgUtil.INSTANCE.loadHomeHotImg(context,mList.get(position).getIcon(), holder.column_iconIv, 6f, -1);
        }

        holder.coulmn_topicTv.setText("今日话题:"+mList.get(position).getToday_topic());
        holder.coulmn_introductionTv.setText(mList.get(position).getIntroduction());
        holder.delIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != checkItemListener) {
                    checkItemListener.itemDel(mList.get(position).getId());
                }
            }
        });
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
        void itemDel(int id);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView  column_iconIv;
        private TextView   coulmn_titleTv;
        private TextView    coulmn_timeTv;
        private TextView    coulmn_topicTv;
        private TextView    coulmn_introductionTv;
        private ImageView   delIv;
        public ViewHolder(View itemView) {
            super(itemView);
            column_iconIv=itemView.findViewById(R.id.iv_column_icon);
            coulmn_titleTv=itemView.findViewById(R.id.tv_coulmn_title);
            coulmn_timeTv=itemView.findViewById(R.id.tv_coulmn_time);
            coulmn_topicTv=itemView.findViewById(R.id.tv_coulmn_topic);
            coulmn_introductionTv=itemView.findViewById(R.id.tv_coulmn_introduction);
            delIv=itemView.findViewById(R.id.iv_del);
        }
    }
}