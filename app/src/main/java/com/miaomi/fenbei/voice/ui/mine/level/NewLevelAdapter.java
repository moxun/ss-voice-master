package com.miaomi.fenbei.voice.ui.mine.level;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.LevelBean;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewLevelAdapter extends RecyclerView.Adapter<NewLevelAdapter.ViewHolder> {

    private Context context;
    private List<LevelBean> list = new ArrayList<>();
    private boolean isWealthLevel;

    public NewLevelAdapter(Context context, boolean isWealthLevel) {
        this.context = context;
        this.isWealthLevel = isWealthLevel;
    }

    public void setData(List<LevelBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewLevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item_level_ml,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewLevelAdapter.ViewHolder holder, int position) {
        if (isWealthLevel){
            holder.iconIv.setWealthLevel(position+1);
        }else{
            holder.iconIv.setCharmLevel(position+1);
        }
        holder.nameTv.setText("LV."+(position+1));
        holder.expTv.setText(""+list.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        LevelView iconIv;
        TextView nameTv;
        private TextView expTv;
        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            expTv = itemView.findViewById(R.id.tv_exp);
        }
    }
}
