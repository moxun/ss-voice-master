package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.Viewholder> {

    private Context context;
    private List<String> list=new ArrayList<>();

    private CheckItemListener mCheckListener;
    public WelcomeAdapter(Context context, CheckItemListener mCheckListener) {

        this.context = context;
        this.mCheckListener=mCheckListener;
    }
    // 添加数据
    public void addData(String msg) {
//   在list中添加数据，并通知条目加入一条
        list.add(msg);
        //添加动画
//        notifyItemInserted(position);
        notifyDataSetChanged();
    }
    // 删除数据
    public void removeData(int position) {
        list.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.welcome_msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
     holder.msgTv.setText(list.get(position));
        holder.iv_del.setOnClickListener(v -> {
            if (mCheckListener != null){
                mCheckListener.itemChecked(list.get(position));
                removeData(position);
            }
        });
    }




    public interface CheckItemListener {
        void itemChecked(String msg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        TextView msgTv;
        ImageView iv_del;
        public Viewholder(View itemView) {
            super(itemView);
            msgTv = itemView.findViewById(R.id.tv_msg);
            iv_del= itemView.findViewById(R.id.iv_del);
        }
    }
}
