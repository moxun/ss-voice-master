package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.AllRoomLableView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendedAdapter extends RecyclerView.Adapter<HomeRecommendedAdapter.ItemHolder> {

    private final List<ChatListBean> mList = new ArrayList<>();
    private final Context mContext;

    public HomeRecommendedAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setData(List<ChatListBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_recommended, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ImgUtil.INSTANCE.loadRoundImg(mContext, mList.get(position).getChat_room_icon(), holder.iv_heart, 8f, -1);
        holder.tv_hot_value.setText(mList.get(position).getHot_value());
        holder.tv_name.setText(mList.get(position).getName());
        holder.tv_label.setLevel(mList.get(position).getLabel());
        holder.itemView.setOnClickListener(v ->
                ChatRoomManager.INSTANCE.joinChat(holder.itemView.getContext(), mList.get(position).getId(), new JoinChatCallBack() {

                    @Override
                    public void onSuc() {

                    }

                    @Override
                    public void onFail(@NotNull String msg) {
                        ToastUtil.INSTANCE.error(holder.itemView.getContext(), msg);
                    }
                }));
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_heart;
        private final TextView tv_name;
        private final AllRoomLableView tv_label;
        private final TextView tv_hot_value;

        ItemHolder(View itemView) {
            super(itemView);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_label = itemView.findViewById(R.id.tv_label);
            tv_hot_value = itemView.findViewById(R.id.tv_hot_value);
        }
    }
}