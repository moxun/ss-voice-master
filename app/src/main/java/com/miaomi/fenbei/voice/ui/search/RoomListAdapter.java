package com.miaomi.fenbei.voice.ui.search;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.RoomListBean;

import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;

import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.PerfectPersonInfoActivity;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomListAdapter  extends RecyclerView.Adapter<RoomListAdapter.ItemHodler> {
    private Context context;
    List<RoomListBean.DataBean> list = new ArrayList<>();

    public RoomListAdapter(Context context) {
        this.context = context;
    }

    public void setData( List<RoomListBean.DataBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHodler holder, final int position) {

        holder.nameTv.setText(list.get(position).getName());
        holder.leaderTv.setText("ID:"+list.get(position).getId());
        ImgUtil.INSTANCE.loadRoundImg(context,list.get(position).getIcon(),holder.iconIv,8f,-1);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ChatRoomManager.INSTANCE.joinChat(context, list.get(position).getId() + "", new JoinChatCallBack() {
                  @Override
                  public void onSuc() {

                  }

                  @Override
                  public void onFail(@NotNull String msg) {
                      ToastUtil.INSTANCE.suc(context, msg);
                  }
              });

          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nameTv;
        TextView leaderTv;

        public ItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            leaderTv = itemView.findViewById(R.id.tv_leader);


        }
    }
}