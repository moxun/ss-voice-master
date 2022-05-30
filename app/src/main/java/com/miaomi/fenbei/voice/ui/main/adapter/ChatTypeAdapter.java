package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.RoomlabelBean;
import com.miaomi.fenbei.voice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatTypeAdapter extends RecyclerView.Adapter<ChatTypeAdapter.Viewholder> {

    private Context context;
    private List<RoomlabelBean> list;
    private OnSelectedListener onSelectedListener;

    public ChatTypeAdapter(Context context, List<RoomlabelBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.selectTv.setSelected(list.get(position).isSelected());
        holder.selectTv.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            for (RoomlabelBean bean : list){
                bean.setSelected(false);
            }
            list.get(position).setSelected(true);
            if (onSelectedListener != null){
                onSelectedListener.onSelected(list.get(position).getId());
            }
            notifyDataSetChanged();
        });
    }


    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener{
        void onSelected(int id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        TextView selectTv;
        public Viewholder(View itemView) {
            super(itemView);
            selectTv = itemView.findViewById(R.id.tv_selected);
        }
    }
}
