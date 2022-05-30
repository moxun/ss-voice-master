package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.GiftWallBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.List;

public class UserCardGiftAdapter extends RecyclerView.Adapter<UserCardGiftAdapter.ViewHolder> {

    private List<GiftWallBean.ListBean> mList;
    private Context context;
//    private int type;

    public UserCardGiftAdapter(List<GiftWallBean.ListBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
//        this.type = type;
    }

    @NonNull
    @Override
    public UserCardGiftAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserCardGiftAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_user_card_gift,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final UserCardGiftAdapter.ViewHolder holder, final int position) {
        if(!TextUtils.isEmpty(mList.get(position).getIcon())){
            ImgUtil.INSTANCE.loadImg(context,mList.get(position).getIcon(),holder.iconIv);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconIv;
        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_gift);
        }
    }
}
