package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RecommandUserBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFriendAdapter extends RecyclerView.Adapter<HomeFriendAdapter.ItemHolder>{

    private List<RecommandUserBean> mList = new ArrayList<>();
    private Context mContext;

    public HomeFriendAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setData(List<RecommandUserBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_all_friend,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
//        if (!TextUtils.isEmpty(mList.get(position).getImg_in())){
//            ImgUtil.INSTANCE.loadFaceIcon(mContext,mList.get(position).getImg_in(), holder.tabiconIv);
//        }



//        holder.itemView.setOnClickListener(v ->
//                ChatRoomManager.INSTANCE.joinChat(mContext, String.valueOf(mList.get(position).getRoom_id()), new JoinChatCallBack() {
//            @Override
//            public void onSuc() {
//
//            }
//
//            @Override
//            public void onFail(@NotNull String msg) {
//
//            }
//        }));
    }

    @Override
    public int getItemCount() {

//        return mList.size();
        return 10;

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_voicecircle_tab_icon);

        }
    }
}
