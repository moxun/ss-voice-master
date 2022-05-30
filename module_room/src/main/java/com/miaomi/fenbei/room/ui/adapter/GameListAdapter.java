package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miaomi.fenbei.base.bean.RoomGameListBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;


public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private List<RoomGameListBean> mList=new ArrayList<>();
    private Context context;

    public GameListAdapter( Context context) {
        this.context = context;
    }
    public void setData(List<RoomGameListBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_game_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GameListAdapter.ViewHolder holder, final int position) {
        ImgUtil.INSTANCE.loadImg(context,mList.get(position).getCover(),holder.iconIv);
        holder.nameTv.setText(mList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != checkItemListener) {
                    checkItemListener.itemChecked(mList.get(position));
                }
            }
        });
    }
    CheckItemListener checkItemListener;
    public void setOnCheckItemListener(CheckItemListener checkItemListener) {
        this.checkItemListener = checkItemListener;
    }
    public interface CheckItemListener {
        void itemChecked(RoomGameListBean roomGameListBean);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nameTv;
        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
        }
    }
}