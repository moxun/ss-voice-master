package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyRoomAdapter extends RecyclerView.Adapter<FamilyRoomAdapter.ItemHodler> {
    private Context context;
    List<FamilyCenterInfoBean.RoomInfoBean> list = new ArrayList<>();

    public FamilyRoomAdapter(Context context) {
        this.context = context;
    }

    public void setData( List<FamilyCenterInfoBean.RoomInfoBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHodler holder, final int position) {
        holder.numTv.setText("人数："+list.get(position).getRoom_num()+"人");
        holder.nameTv.setText(list.get(position).getRoom_name());
        holder.leaderTv.setText("房主:"+list.get(position).getRoom_nickname());
        ImgUtil.INSTANCE.loadRoundImg(context,list.get(position).getRoom_icon(),holder.iconIv,8f,-1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/3/11  房间
                ChatRoomManager.INSTANCE.joinChat(context, String.valueOf(list.get(position).getRoom_id()), new JoinChatCallBack() {
                    @Override
                    public void onSuc() {

                    }

                    @Override
                    public void onFail(@NotNull String msg) {
                        ToastUtil.INSTANCE.error(context,msg);
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
        TextView numTv;
        TextView enterTv;
        public ItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            leaderTv = itemView.findViewById(R.id.tv_leader);
            numTv = itemView.findViewById(R.id.tv_num);
            enterTv = itemView.findViewById(R.id.tv_enter);
        }
    }
}
