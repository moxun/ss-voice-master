package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.RandomRoomBean;
import com.miaomi.fenbei.base.bean.RowBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.callback.CloseRoomDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RandomRoomAdapter extends RecyclerView.Adapter<RandomRoomAdapter.ItemHolder>{

    private List<RandomRoomBean> mList = new ArrayList<>();
    private Context mContext;
    private CloseRoomDialogListener mCheckListener;

    public void setmCheckListener(CloseRoomDialogListener mCheckListener) {
        this.mCheckListener = mCheckListener;
    }

    //    private int type;
    public RandomRoomAdapter(Context mContext) {
//        this.mCheckListener=mCheckListener;
        this.mContext = mContext;
    }

    public void setData(List<RandomRoomBean> list){
        mList.clear();
        mList.addAll(list);
//        this.type=type;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RandomRoomAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRoomAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_random_room,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRoomAdapter.ItemHolder holder, final int position) {
        ImgUtil.INSTANCE.loadRoundImg(mContext,mList.get(position).getIcon(), holder.faceIv,8f,-1);
        holder.nameTv.setText(mList.get(position).getName());
        holder.hotvalueTv.setText(mList.get(position).getContribute());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckListener != null){
                    mCheckListener.onEnterRoom(""+mList.get(position).getId());
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView faceIv;
        private TextView nameTv;
        private TextView hotvalueTv;
        ItemHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            hotvalueTv=itemView.findViewById(R.id.tv_hot_value);
            faceIv=itemView.findViewById(R.id.iv_face);
        }
    }
}
