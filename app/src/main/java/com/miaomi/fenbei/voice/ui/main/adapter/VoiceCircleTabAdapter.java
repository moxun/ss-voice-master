package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RoomlabelBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceCircleTabAdapter extends RecyclerView.Adapter<VoiceCircleTabAdapter.ItemHolder>{

    private List<RoomlabelBean> mList = new ArrayList<>();
    private Context mContext;
    private CheckItemListener mCheckListener;
    public VoiceCircleTabAdapter(Context mContext,CheckItemListener mCheckListener) {
        this.mContext = mContext;
        this.mCheckListener=mCheckListener;
    }

    public void setData(List<RoomlabelBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_voicecircle_tab,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (!TextUtils.isEmpty(mList.get(position).getImg_in())){
            ImgUtil.INSTANCE.loadImg(mContext,mList.get(position).getImg_in(), holder.tabiconIv);
        }

        holder.itemView.setOnClickListener(v ->{
            if (null != mCheckListener) {
                if (position==0){
                    mCheckListener.itemChecked("");
                }else{
                    mCheckListener.itemChecked(""+mList.get(position).getId());
                }

            }
        });

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
    public interface CheckItemListener {
        void itemChecked(String id);
    }
    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_voicecircle_tab_icon);

        }
    }
}
