package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.base.bean.HomepageBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.callback.CloseRoomDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserOnlineAdapter extends RecyclerView.Adapter<UserOnlineAdapter.ItemHolder>{

    private List<HomepageBean> mList = new ArrayList<>();
    private Context mContext;
    private CloseRoomDialogListener mCheckListener;

    public void setmCheckListener(CloseRoomDialogListener mCheckListener) {
        this.mCheckListener = mCheckListener;
    }

    public UserOnlineAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<HomepageBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user_online,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
//        ImgUtil.INSTANCE.loadFaceIcon(mContext, DataHelper.INSTANCE.getUserInfo().getFace(), holder.iconIv);
//        holder.iconIv.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.home_page_head));
//        holder.iconWv.start();
//        holder.tv_name.setText(mList.get(position).getUser_id()+"");
        ImgUtil.INSTANCE.loadFaceIcon(mContext,mList.get(position).getFace(), holder.iconIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckListener != null){
                    mCheckListener.onEnterRoom(String.valueOf(mList.get(position).getRoom_id()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView iconIv;
        private TextView tv_name;
        ItemHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
//            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
