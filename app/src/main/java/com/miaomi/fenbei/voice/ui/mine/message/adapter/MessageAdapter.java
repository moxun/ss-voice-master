package com.miaomi.fenbei.voice.ui.mine.message.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.MessageBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {

    private List<MessageBean> mData = new ArrayList<>();

    public void setData(List<MessageBean> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(List<MessageBean> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_message_content, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        holder.tvMessageTitle.setText(mData.get(position).getTitle());
        holder.tvMessageContent.setText(mData.get(position).getNote());
        if (mData.get(position).getNumber() == 0){
            holder.tvMessageTime.setVisibility(View.VISIBLE);
            holder.tvMessageTime.setText(mData.get(position).getDate());
        }else {
            holder.tvMessageTime.setVisibility(View.GONE);
        }
        if (mData.get(position).getStatus() != 0){
            holder.tvMessageTitle.setCompoundDrawables(null,null,null,null);
        }

        if (TextUtils.isEmpty(mData.get(position).getUrl())){
            holder.checkTv.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }else{
            holder.checkTv.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
        }

        holder.checkTv.setOnClickListener(v -> ARouter.getInstance().build(Uri.parse(mData.get(position).getUrl())).navigation());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvMessageTime;
        private TextView tvMessageTitle;
        private TextView tvMessageContent;
        private TextView checkTv;
        private View line;
        public ItemHolder(View itemView) {
            super(itemView);
            tvMessageTime = itemView.findViewById(R.id.tv_message_time);
            tvMessageTitle = itemView.findViewById(R.id.tv_message_title);
            tvMessageContent = itemView.findViewById(R.id.tv_message_content);
            checkTv = itemView.findViewById(R.id.tv_check);
            line = itemView.findViewById(R.id.line);
        }

    }
}
