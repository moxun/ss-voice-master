package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.base.widget.HeartMeView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceCircleRoomListAdapter extends RecyclerView.Adapter<VoiceCircleRoomListAdapter.ItemHolder>{

    private List<ChatListBean> mList = new ArrayList<>();
    private Context mContext;

    public VoiceCircleRoomListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<ChatListBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_voicecircle_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ImgUtil.INSTANCE.loadImg(mContext,mList.get(position).getLabel_img(),holder.lableIv);
        holder.heartMeView.setContent(mList.get(position).getUp_user());
        ImgUtil.INSTANCE.loadHomeHotImg(mContext,mList.get(position).getChat_room_icon(), holder.tabiconIv,6f,-1);
        ImgUtil.INSTANCE.loadGif(mContext, R.drawable.base_icon_room_online, holder.room_gifIv);
//        holder.roomtabIv.setLevel(mList.get(position).getLabel());
        holder.online_num.setText(mList.get(position).getHot_value());
        holder.room_toppicTv.setText(mList.get(position).getChat_room_name());
        holder.itemView.setOnClickListener(v ->
                ChatRoomManager.INSTANCE.joinChat(mContext, String.valueOf(mList.get(position).getChat_room_id()), new JoinChatCallBack() {
            @Override
            public void onSuc() {

            }

            @Override
            public void onFail( String msg) {
                ToastUtil.INSTANCE.error(holder.itemView.getContext(),msg);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        private ImageView lableIv;
        private ImageView room_gifIv;
        private DCBTextView online_num;
        private TextView room_toppicTv;
        private HeartMeView heartMeView;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_avter);
            lableIv=itemView.findViewById(R.id.iv_room_lable);
            room_gifIv=itemView.findViewById(R.id.iv_room_gif);
            online_num=itemView.findViewById(R.id.online_num);
            room_toppicTv=itemView.findViewById(R.id.tv_room_toppic);
            heartMeView = itemView.findViewById(R.id.heart_view);

        }
    }
}
