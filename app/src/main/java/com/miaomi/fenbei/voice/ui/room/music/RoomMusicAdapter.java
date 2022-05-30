package com.miaomi.fenbei.voice.ui.room.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.db.MusicBean;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomMusicAdapter extends RecyclerView.Adapter<RoomMusicAdapter.ItemHolder> {
    private Context context;
    private List<MusicBean> mList = new ArrayList<>();
    private int musicType;

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    public RoomMusicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MusicBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<MusicBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_hot_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        if (mList.get(position).isPlaying()){
            holder.playIv.setVisibility(View.VISIBLE);
            holder.positionTv.setText("");
        }else{
            holder.playIv.setVisibility(View.GONE);
            holder.positionTv.setText(String.valueOf(position+1));
        }
        holder.nameTv.setText(mList.get(position).getName());
        holder.sizeTv.setText(mList.get(position).getSize()+"MB   "+mList.get(position).getSinger());
        if (musicType == RoomMusicFragment.TYPE_MUSIC_LOCAL){
            holder.uploadTv.setText(mList.get(position).getUploader());
        }else{
            holder.uploadTv.setText("上传人："+mList.get(position).getUploader());
        }
        holder.addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onAdd(mList.get(position));
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && musicType != RoomMusicFragment.TYPE_MUSIC_UPLOAD){
                    for (MusicBean bean :mList){
                        bean.setPlaying(false);
                    }
                    mList.get(position).setPlaying(true);
                    onItemClickListener.onItemClick(mList.get(position));
                    notifyDataSetChanged();
                }
            }
        });
    }
    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onAdd(MusicBean bean);
        void onItemClick(MusicBean bean);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView sizeTv;
        TextView uploadTv;
        TextView positionTv;
        ImageView addIv;
        ImageView playIv;
        public ItemHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            sizeTv = itemView.findViewById(R.id.tv_size);
            uploadTv = itemView.findViewById(R.id.tv_upload);
            positionTv = itemView.findViewById(R.id.tv_position);
            addIv = itemView.findViewById(R.id.iv_add_music);
            playIv = itemView.findViewById(R.id.iv_isplaying);
        }
    }
}
